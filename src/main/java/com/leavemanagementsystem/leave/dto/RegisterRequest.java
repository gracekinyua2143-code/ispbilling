package com.leavemanagementsystem.leave.dto;



import lombok.Data;

@Data
public class RegisterRequest {

    private String staffNo;
    private String username;
    private String password;
}