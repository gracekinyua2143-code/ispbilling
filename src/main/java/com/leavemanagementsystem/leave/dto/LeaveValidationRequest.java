package com.leavemanagementsystem.leave.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveValidationRequest {

    private Long employeeId;

    private Long leaveTypeId;

    private Long relieverId;

    private String shift;

    private LocalDate startDate;

    private LocalDate endDate;

}