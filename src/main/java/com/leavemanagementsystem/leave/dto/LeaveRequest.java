package com.leavemanagementsystem.leave.dto;

import com.leavemanagementsystem.leave.entiy.Shift;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequest {

    private Long employeeId;

    private Long leaveTypeId;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate reportingDate;

    private Shift shift;

    private Long relieverId;

    private Long approverId;

}