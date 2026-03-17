package com.leavemanagementsystem.leave.service;

import com.leavemanagementsystem.leave.dto.*;
import com.leavemanagementsystem.leave.entiy.*;
import com.leavemanagementsystem.leave.repository.LeaveBalanceRepository;
import com.leavemanagementsystem.leave.repository.LeaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveReportsService {

    private final LeaveRepository leaveRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;

    // 1. Department leave report
    public List<DepartmentLeaveReport> departmentReport(Long departmentId){
        return leaveRepository.findAll()
                .stream()
                .filter(l -> l.getEmployee().getDepartment().getId().equals(departmentId))
                .map(l -> new DepartmentLeaveReport(
                        l.getEmployee().getDepartment().getName(),
                        l.getEmployee().getFullName(),
                        l.getLeaveType().getName(),
                        l.getStartDate(),
                        l.getEndDate(),
                        l.getShift().name(),
                        l.getStatus()
                ))
                .collect(Collectors.toList());
    }

    // 2. Station leave report
    public List<StationLeaveReport> stationReport(Long stationId){
        return leaveRepository.findAll()
                .stream()
                .filter(l -> l.getEmployee().getStation().getId().equals(stationId))
                .map(l -> new StationLeaveReport(
                        l.getEmployee().getStation().getName(),
                        l.getEmployee().getFullName(),
                        l.getLeaveType().getName(),
                        l.getStartDate(),
                        l.getEndDate(),
                        l.getShift().name(),
                        l.getStatus()
                ))
                .collect(Collectors.toList());
    }

    // 3. Monthly leave report
    public List<MonthlyLeaveReport> monthlyReport(int year, int month){
        return leaveRepository.findAll()
                .stream()
                .filter(l -> l.getStartDate().getYear() == year
                        && l.getStartDate().getMonthValue() == month)
                .map(l -> new MonthlyLeaveReport(
                        year,
                        month,
                        l.getEmployee().getDepartment().getName(),
                        l.getEmployee().getFullName(),
                        l.getLeaveType().getName(),
                        (int) java.time.temporal.ChronoUnit.DAYS.between(
                                l.getStartDate(), l.getEndDate()) + 1
                ))
                .collect(Collectors.toList());
    }

    // 4. Employee leave history
    public List<EmployeeLeaveHistory> employeeHistory(Long employeeId){
        return leaveRepository.findAll()
                .stream()
                .filter(l -> l.getEmployee().getId().equals(employeeId))
                .map(l -> new EmployeeLeaveHistory(
                        l.getEmployee().getFullName(),
                        l.getEmployee().getDepartment().getName(),
                        l.getLeaveType().getName(),
                        l.getStartDate(),
                        l.getEndDate(),
                        l.getShift().name(),
                        l.getStatus()
                ))
                .collect(Collectors.toList());
    }

    // 5. Leave balance report
    public List<LeaveBalanceReport> leaveBalanceReport(){
        return leaveBalanceRepository.findAll()
                .stream()
                .map(b -> new LeaveBalanceReport(
                        b.getEmployee().getFullName(),
                        b.getEmployee().getDepartment().getName(),
                        b.getLeaveType().getName(),
                        b.getUsedDays(),
                        b.getRemainingDays(),
                        b.getYear()
                ))
                .collect(Collectors.toList());
    }

}