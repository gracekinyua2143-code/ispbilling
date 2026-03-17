package com.leavemanagementsystem.leave.controler;

import com.leavemanagementsystem.leave.dto.LeaveRequest;
import com.leavemanagementsystem.leave.entiy.Employee;
import com.leavemanagementsystem.leave.entiy.Leave;
import com.leavemanagementsystem.leave.entiy.LeaveType;
import com.leavemanagementsystem.leave.entiy.User;
import com.leavemanagementsystem.leave.repository.EmployeeRepository;
import com.leavemanagementsystem.leave.repository.LeaveRepository;
import com.leavemanagementsystem.leave.security.CustomUserDetails;
import com.leavemanagementsystem.leave.service.LeaveEngineService;
import com.leavemanagementsystem.leave.service.LeaveTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")
public class EmployeeController {

    private final LeaveRepository leaveRepository;
    private final LeaveEngineService leaveEngineService;
    private final EmployeeRepository employeeRepository;
    private final LeaveTypeService leaveTypeService;

    @PostMapping("/apply-leave")
    public Leave applyLeave(@RequestBody LeaveRequest request){
        return leaveEngineService.applyLeave(request);
    }

    @GetMapping("/leave-types")
    public List<LeaveType> getLeaveTypes(){
        return leaveTypeService.getAll();
    }

    @GetMapping("/my-leaves")
    public List<Leave> myLeaves(@AuthenticationPrincipal CustomUserDetails userDetails){

        User user = userDetails.getUser();

        return leaveRepository.findByEmployeeId(
                user.getEmployee().getId()
        );
    }

    // ================================
    // ✅ 1. LATEST LEAVES (ORDERED)
    // ================================
    @GetMapping("/my-leaves-latest")
    public List<Leave> myLeavesLatest(@AuthenticationPrincipal CustomUserDetails userDetails){

        User user = userDetails.getUser();

        return leaveRepository
                .findByEmployeeId(user.getEmployee().getId())
                .stream()
                .sorted(Comparator.comparing(Leave::getId).reversed())
                .collect(Collectors.toList());
    }

    // ================================
    // ✅ RELIEVERS (DB LEVEL)
    // ================================
// ================================
// ✅ RELIEVERS (DB LEVEL, exclude self)
// ================================
    @GetMapping("/relievers")
    public List<Employee> getRelievers(@AuthenticationPrincipal CustomUserDetails userDetails){

        User user = userDetails.getUser();

        Long stationId = user.getEmployee().getStation().getId();
        Long currentEmployeeId = user.getEmployee().getId(); // current employee

        Set<String> excludedRoles = Set.of(
                "SUPER_ADMIN","ADMIN","HR","TM","CM","INTERNAL_AUDITOR","DM"
        );

        // Fetch relievers from DB
        List<Employee> relievers = employeeRepository.findRelievers(stationId, excludedRoles);

        // Exclude the current employee
        return relievers.stream()
                .filter(e -> !e.getId().equals(currentEmployeeId))
                .collect(Collectors.toList());
    }
    // ================================
    // ✅ APPROVERS (DB LEVEL)
    // ================================
    @GetMapping("/approvers")
    public List<Employee> getApprovers(@AuthenticationPrincipal CustomUserDetails userDetails){

        User user = userDetails.getUser();

        Long stationId = user.getEmployee().getStation().getId();

        List<Employee> dms = employeeRepository.findDMsInStation(stationId);

        List<Employee> globals = employeeRepository.findGlobalApprovers();

        // merge both lists
        dms.addAll(globals);

        return dms;
    }
}