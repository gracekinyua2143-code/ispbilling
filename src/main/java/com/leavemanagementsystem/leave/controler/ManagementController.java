package com.leavemanagementsystem.leave.controler;

import com.leavemanagementsystem.leave.entiy.Leave;
import com.leavemanagementsystem.leave.entiy.User;
import com.leavemanagementsystem.leave.repository.LeaveRepository;
import com.leavemanagementsystem.leave.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/management")
@RequiredArgsConstructor
public class ManagementController {

    private final LeaveRepository leaveRepository;
    private final LeaveService leaveService;
    @GetMapping("/leaves")
    public List<Leave> getLeaves(){

        return leaveRepository.findAll()
                .stream()
                .filter(l ->
                        !l.getEmployee()
                                .getDepartment()
                                .getName()
                                .equalsIgnoreCase("HR")
                )
                .toList();
    }

    @PutMapping("/approve/{leaveId}")
    public Leave approveLeave(@PathVariable Long leaveId,
                              @AuthenticationPrincipal User user){

        return leaveService.approveLeave(
                leaveId,
                user.getEmployee()
        );
    }
}