package com.leavemanagementsystem.leave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyLeaveReport {
    private int year;
    private int month;
    private String departmentName;
    private String employeeName;
    private String leaveType;
    private int totalDays;
}
