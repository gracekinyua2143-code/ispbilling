package com.leavemanagementsystem.leave.service;

import com.leavemanagementsystem.leave.dto.LeaveRequest;
import com.leavemanagementsystem.leave.entiy.*;
import com.leavemanagementsystem.leave.repository.EmployeeRepository;
import com.leavemanagementsystem.leave.repository.LeaveRepository;
import com.leavemanagementsystem.leave.repository.LeaveTypeRepository;
import com.leavemanagementsystem.leave.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class LeaveEngineService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final NotificationService notificationService;

    public Leave applyLeave(LeaveRequest request){

        // ✅ Step 1: Get logged-in employee
        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();

        User user = userDetails.getUser();
        if(user.getEmployee() == null){
            throw new IllegalStateException("Logged-in user is not linked to an employee");
        }

        Employee employee = employeeRepository.findById(user.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // ✅ Step 2: Validate reliever and approver IDs
        if(request.getRelieverId() == null){
            throw new IllegalArgumentException("Reliever ID must not be null");
        }
        if(request.getApproverId() == null){
            throw new IllegalArgumentException("Approver ID must not be null");
        }

        Employee reliever = employeeRepository.findById(request.getRelieverId())
                .orElseThrow(() -> new RuntimeException("Reliever not found"));

        Employee approver = employeeRepository.findById(request.getApproverId())
                .orElseThrow(() -> new RuntimeException("Approver not found"));

        // ✅ Step 3: Validate leave type
        LeaveType leaveType = leaveTypeRepository.findById(request.getLeaveTypeId())
                .orElseThrow(() -> new RuntimeException("Leave type not found"));

        Department department = employee.getDepartment();

        // 1️⃣ CHECK SHIFT CAPACITY
        long currentLeaves =
                leaveRepository.countDepartmentLeaves(
                        department.getId(),
                        request.getShift(),
                        request.getStartDate(),
                        request.getEndDate()
                );

        if(currentLeaves >= department.getRemittanceLimit()){
            throw new RuntimeException(
                    "Selected dates are full. Please choose another date."
            );
        }

        // 2️⃣ RELIEVER CONFLICT
        long relieverConflict =
                leaveRepository.relieverConflict(
                        reliever.getId(),
                        request.getStartDate(),
                        request.getEndDate()
                );

        if(relieverConflict > 0){
            throw new RuntimeException(
                    "Reliever already assigned during selected dates"
            );
        }

        // 3️⃣ ANNUAL LEAVE MONTHLY LIMIT
        if(leaveType.getName().equalsIgnoreCase("ANNUAL")){

            long days =
                    ChronoUnit.DAYS.between(
                            request.getStartDate(),
                            request.getEndDate()
                    ) + 1;

            if(days > 2){
                throw new RuntimeException(
                        "Annual leave cannot exceed 2 days per month"
                );
            }
        }

        // 4️⃣ STATION CHECK FOR DM
        if(!isSpecialDepartment(employee.getDepartment().getName())){
            if(!employee.getStation().getId()
                    .equals(approver.getStation().getId())){
                throw new RuntimeException(
                        "DM must be from the same station"
                );
            }
        }

        // 5️⃣ CHECK FOR OVERLAPPING LEAVES
        long overlaps =
                leaveRepository.countOverlappingLeaves(
                        employee.getId(),
                        request.getStartDate(),
                        request.getEndDate()
                );

        if(overlaps > 0){
            throw new RuntimeException(
                    "You already have leave during the selected dates"
            );
        }

        // 6️⃣ CREATE LEAVE
        Leave leave = new Leave();
        leave.setEmployee(employee);
        leave.setLeaveType(leaveType);
        leave.setStartDate(request.getStartDate());
        leave.setEndDate(request.getEndDate());
        leave.setReportingDate(request.getReportingDate());
        leave.setShift(request.getShift());
        leave.setReliever(reliever);
        leave.setApprover(approver);
        leave.setStatus("PENDING");

        leaveRepository.save(leave);

        // 7️⃣ NOTIFY APPROVER
        // notificationService.sendNotification(approver.getUser(),
        //      employee.getFullName() + " has applied for leave requiring your approval"
        // );

        return leave;
    }

    private boolean isSpecialDepartment(String name){
        return name.equalsIgnoreCase("HR") ||
                name.equalsIgnoreCase("TM") ||
                name.equalsIgnoreCase("CM") ||
                name.equalsIgnoreCase("INTERNAL AUDITOR") ||
                name.equalsIgnoreCase("OPM");
    }
}