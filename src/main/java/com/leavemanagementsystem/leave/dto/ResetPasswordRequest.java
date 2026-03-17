package com.leavemanagementsystem.leave.dto;



import lombok.Data;

@Data
public class ResetPasswordRequest {

    private String staffNo;
    private String newPassword;
}