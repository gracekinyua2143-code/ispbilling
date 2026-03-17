package com.leavemanagementsystem.leave.controler;

import com.leavemanagementsystem.leave.dto.LeaveRequest;
import com.leavemanagementsystem.leave.entiy.Leave;
import com.leavemanagementsystem.leave.service.LeaveEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveEngineService leaveEngineService;

   // @PostMapping("/apply-leave")
   // public Leave applyLeave(@RequestBody LeaveRequest request){

       // return leaveEngineService.applyLeave(request);
  //  }

}