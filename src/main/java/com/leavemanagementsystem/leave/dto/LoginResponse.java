package com.leavemanagementsystem.leave.dto;



import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class LoginResponse {

    private String token;

    private String username;

    private String employeeName;

    private String department;

    private Set<String> roles;
}