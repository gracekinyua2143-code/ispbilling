package com.leavemanagementsystem.leave.controler;

import com.leavemanagementsystem.leave.dto.RejectLeaveRequest;
import com.leavemanagementsystem.leave.entiy.Employee;
import com.leavemanagementsystem.leave.entiy.Leave;
import com.leavemanagementsystem.leave.entiy.User;
import com.leavemanagementsystem.leave.repository.UserRepository;
import com.leavemanagementsystem.leave.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final LeaveService leaveService;
    private final UserRepository userRepository;

    // VIEW PENDING LEAVES
    @GetMapping("/pending-leaves")
    public List<Leave> getPendingLeaves(){

        return leaveService.getPendingLeaves();
    }

    // APPROVE LEAVE
    @PutMapping("/approve-leave/{leaveId}")
    public Leave approveLeave(@PathVariable Long leaveId,
                              Authentication authentication){

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        Employee approver = user.getEmployee();

        return leaveService.approveLeave(leaveId, approver);
    }

    // REJECT LEAVE
    @PutMapping("/reject-leave/{leaveId}")
    public Leave rejectLeave(@PathVariable Long leaveId,
                             @RequestBody RejectLeaveRequest request,
                             Authentication authentication){

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        Employee approver = user.getEmployee();

        return leaveService.rejectLeave(
                leaveId,
                approver,
                request.getReason()
        );
    }

}