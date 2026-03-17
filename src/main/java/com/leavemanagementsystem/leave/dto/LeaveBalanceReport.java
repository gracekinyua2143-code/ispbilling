package com.leavemanagementsystem.leave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeaveBalanceReport {
    private String employeeName;
    private String departmentName;
    private String leaveType;
    private int usedDays;
    private int remainingDays;
    private int year;
}
