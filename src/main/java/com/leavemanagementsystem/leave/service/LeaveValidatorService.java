package com.leavemanagementsystem.leave.service;

import com.leavemanagementsystem.leave.dto.*;
import com.leavemanagementsystem.leave.entiy.*;
import com.leavemanagementsystem.leave.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class LeaveValidatorService {

    private final EmployeeRepository employeeRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final LeaveRepository leaveRepository;
    private final LeaveBalanceService leaveBalanceService;
    private final ShiftAvailabilityService shiftAvailabilityService;

    public LeaveValidationResponse validate(LeaveValidationRequest request){

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow();

        LeaveType leaveType = leaveTypeRepository.findById(request.getLeaveTypeId())
                .orElseThrow();

        long days = ChronoUnit.DAYS.between(
                request.getStartDate(),
                request.getEndDate()
        ) + 1;

        // 1️⃣ Check leave balance
        LeaveBalance balance =
                leaveBalanceService.getOrCreateBalance(employee, leaveType);

        if(balance.getRemainingDays() < days){
            return new LeaveValidationResponse(
                    false,
                    "Insufficient leave balance"
            );
        }

        // 2️⃣ Overlap check
        long overlaps =
                leaveRepository.countOverlappingLeaves(
                        employee.getId(),
                        request.getStartDate(),
                        request.getEndDate()
                );

        if(overlaps > 0){
            return new LeaveValidationResponse(
                    false,
                    "Leave overlaps with existing leave"
            );
        }

        // 3️⃣ Reliever conflict
        boolean relieverBusy =
                leaveRepository.existsRelieverConflict(
                        request.getRelieverId(),
                        request.getStartDate(),
                        request.getEndDate()
                );

        if(relieverBusy){
            return new LeaveValidationResponse(
                    false,
                    "Selected reliever is already assigned during these dates"
            );
        }

        // 4️⃣ Shift availability
        boolean shiftAvailable =
                shiftAvailabilityService.isShiftAvailable(
                        employee.getDepartment().getId(),
                        request.getShift(),
                        request.getStartDate(),
                        request.getEndDate()
                );

        if(!shiftAvailable){
            return new LeaveValidationResponse(
                    false,
                    "Selected dates are full. Please choose another date."
            );
        }

        // 5️⃣ Monthly leave limit
        if(leaveType.getMonthlyLimit() != null){

            if(days > leaveType.getMonthlyLimit()){

                return new LeaveValidationResponse(
                        false,
                        "Monthly leave limit exceeded"
                );
            }
        }

        return new LeaveValidationResponse(
                true,
                "Leave request is valid"
        );
    }
}