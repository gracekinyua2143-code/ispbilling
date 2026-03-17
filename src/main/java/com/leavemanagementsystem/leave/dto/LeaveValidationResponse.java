package com.leavemanagementsystem.leave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeaveValidationResponse {

    private boolean allowed;
    private String message;

}