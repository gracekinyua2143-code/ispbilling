package com.leavemanagementsystem.leave.service;

import com.leavemanagementsystem.leave.entiy.*;
import com.leavemanagementsystem.leave.repository.LeaveBalanceRepository;
import com.leavemanagementsystem.leave.repository.LeaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final LeaveBalanceService leaveBalanceService;
    private final LeaveBalanceRepository leaveBalanceRepository;
    private final NotificationService notificationService;

    public List<Leave> getPendingLeaves(){
        return leaveRepository.findByStatus("PENDING");
    }

    public Leave approveLeave(Long leaveId, Employee approver){

        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        // station restriction
        if(!isSpecialDepartment(approver.getDepartment().getName())){

            if(!approver.getStation().getId()
                    .equals(leave.getEmployee().getStation().getId())){

                throw new RuntimeException(
                        "You cannot approve leave outside your station"
                );
            }
        }

        // 🔥 CALCULATE NUMBER OF LEAVE DAYS
        long days =
                ChronoUnit.DAYS.between(
                        leave.getStartDate(),
                        leave.getEndDate()
                ) + 1;

        // 🔥 GET OR CREATE BALANCE
        LeaveBalance balance =
                leaveBalanceService.getOrCreateBalance(
                        leave.getEmployee(),
                        leave.getLeaveType()
                );

        // 🔥 CHECK SUFFICIENT BALANCE
        if(balance.getRemainingDays() < days){
            throw new RuntimeException(
                    "Insufficient leave balance"
            );
        }
        long overlaps =
                leaveRepository.countOverlappingLeaves(
                        leave.getEmployee().getId(),
                        leave.getStartDate(),
                        leave.getEndDate()
                );

        if(overlaps > 1){
            throw new RuntimeException(
                    "Leave overlaps with another approved leave"
            );
        }
        // 🔥 DEDUCT LEAVE DAYS
        balance.setUsedDays(
                balance.getUsedDays() + (int) days
        );

        balance.setRemainingDays(
                balance.getRemainingDays() - (int) days
        );

        leaveBalanceRepository.save(balance);

        // 🔥 APPROVE LEAVE
        leave.setStatus("APPROVED");

        leaveRepository.save(leave);

        // notify employee
        /*
        notificationService.sendNotification(
                leave.getEmployee().getUser(),
                "Your leave has been approved"
        );
        */

        return leave;
    }

    public Leave rejectLeave(Long leaveId,
                             Employee approver,
                             String reason){

        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        if(!isSpecialDepartment(approver.getDepartment().getName())){

            if(!approver.getStation().getId()
                    .equals(leave.getEmployee().getStation().getId())){

                throw new RuntimeException(
                        "You cannot reject leave outside your station"
                );
            }
        }

        leave.setStatus("REJECTED");

        leaveRepository.save(leave);

        /*
        notificationService.sendNotification(
                leave.getEmployee().getUser(),
                "Your leave request was rejected. Reason: " + reason
        );
        */

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