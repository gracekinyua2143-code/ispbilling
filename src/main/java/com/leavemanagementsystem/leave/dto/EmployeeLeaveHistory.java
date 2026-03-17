package com.leavemanagementsystem.leave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EmployeeLeaveHistory {
    private String employeeName;
    private String departmentName;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String shift;
    private String status;
}
