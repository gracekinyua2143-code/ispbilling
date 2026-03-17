package com.leavemanagementsystem.leave.service;

import com.leavemanagementsystem.leave.entiy.*;
import com.leavemanagementsystem.leave.repository.LeaveBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LeaveBalanceService {

    private final LeaveBalanceRepository leaveBalanceRepository;

    public LeaveBalance getOrCreateBalance(Employee employee,
                                           LeaveType leaveType){

        int year = LocalDate.now().getYear();

        return leaveBalanceRepository
                .findByEmployeeIdAndLeaveTypeIdAndYear(
                        employee.getId(),
                        leaveType.getId(),
                        year
                )
                .orElseGet(() -> createBalance(employee, leaveType, year));
    }

    private LeaveBalance createBalance(Employee employee,
                                       LeaveType leaveType,
                                       int year){

        LeaveBalance balance = new LeaveBalance();

        balance.setEmployee(employee);
        balance.setLeaveType(leaveType);

        balance.setTotalDays(leaveType.getMaxDays());
        balance.setUsedDays(0);

        balance.setRemainingDays(leaveType.getMaxDays());

        balance.setCarryForwardDays(0);

        balance.setYear(year);

        return leaveBalanceRepository.save(balance);
    }

}