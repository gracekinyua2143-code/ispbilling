package com.leavemanagementsystem.leave.controler;

import com.leavemanagementsystem.leave.entiy.Employee;
import com.leavemanagementsystem.leave.entiy.Leave;
import com.leavemanagementsystem.leave.entiy.User;
import com.leavemanagementsystem.leave.repository.EmployeeRepository;
import com.leavemanagementsystem.leave.repository.LeaveRepository;
import com.leavemanagementsystem.leave.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hr")
@RequiredArgsConstructor
public class HrController {

    private final LeaveService leaveService;
    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    @GetMapping("/leaves")
    public List<Leave> allLeaves(){
        return leaveRepository.findAll();
    }

    @PutMapping("/approve/{leaveId}")
    public Leave approveLeave(@PathVariable Long leaveId,
                              @AuthenticationPrincipal User user){

        return leaveService.approveLeave(leaveId,
                user.getEmployee());
    }

    @GetMapping("/employees")
    public List<Employee> allStaff(){
        return employeeRepository.findAll();
    }

}