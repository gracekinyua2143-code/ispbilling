package com.leavemanagementsystem.leave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DepartmentLeaveReport {
    private String departmentName;
    private String employeeName;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String shift;
    private String status;
}

