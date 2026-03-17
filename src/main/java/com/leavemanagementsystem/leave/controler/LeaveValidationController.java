package com.leavemanagementsystem.leave.controler;

import com.leavemanagementsystem.leave.dto.*;
import com.leavemanagementsystem.leave.service.LeaveValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leave")
@RequiredArgsConstructor
public class LeaveValidationController {

    private final LeaveValidatorService leaveValidatorService;

    @PostMapping("/validate")
    public LeaveValidationResponse validateLeave(
            @RequestBody LeaveValidationRequest request){

        return leaveValidatorService.validate(request);
    }
}