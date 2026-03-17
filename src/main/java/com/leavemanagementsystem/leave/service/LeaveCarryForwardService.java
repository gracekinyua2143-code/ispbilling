package com.leavemanagementsystem.leave.service;

import com.leavemanagementsystem.leave.entiy.LeaveBalance;
import com.leavemanagementsystem.leave.repository.LeaveBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveCarryForwardService {

    private final LeaveBalanceRepository leaveBalanceRepository;

    @Scheduled(cron = "0 0 1 1 1 ?")
    public void carryForward(){

        int previousYear = LocalDate.now().getYear() - 1;

        List<LeaveBalance> balances =
                leaveBalanceRepository.findAll();

        for(LeaveBalance balance : balances){

            if(balance.getYear() != previousYear)
                continue;

            int carry =
                    Math.min(balance.getRemainingDays(),5);

            LeaveBalance newBalance = new LeaveBalance();

            newBalance.setEmployee(balance.getEmployee());
            newBalance.setLeaveType(balance.getLeaveType());

            newBalance.setYear(previousYear + 1);

            newBalance.setCarryForwardDays(carry);

            int total =
                    balance.getLeaveType().getMaxDays() + carry;

            newBalance.setTotalDays(total);

            newBalance.setRemainingDays(total);

            newBalance.setUsedDays(0);

            leaveBalanceRepository.save(newBalance);
        }
    }
}