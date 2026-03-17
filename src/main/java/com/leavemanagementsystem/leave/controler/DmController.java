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
@RequestMapping("/api/dm")
@RequiredArgsConstructor
public class DmController {

    private final LeaveService leaveService;
    private final LeaveRepository leaveRepository;

    @GetMapping("/leaves")
    public List<Leave> departmentLeaves(
            @AuthenticationPrincipal User user){

        Long stationId = user.getEmployee()
                .getStation()
                .getId();

        return leaveRepository.findAll()
                .stream()
                .filter(l ->
                        l.getEmployee()

                                .getStation()
                                .getId()
                                .equals(stationId)

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