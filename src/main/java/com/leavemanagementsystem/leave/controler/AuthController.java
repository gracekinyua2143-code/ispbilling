package com.leavemanagementsystem.leave.controler;

import com.leavemanagementsystem.leave.dto.*;
import com.leavemanagementsystem.leave.entiy.User;
import com.leavemanagementsystem.leave.service.AuthService;
import com.leavemanagementsystem.leave.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        return authService.login(request);
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request){
        return userService.register(request);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody ResetPasswordRequest request){

        userService.resetPassword(request);

        return "Password reset successfully";
    }
}