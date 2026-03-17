package com.leavemanagementsystem.leave.service;

import com.leavemanagementsystem.leave.dto.*;
import com.leavemanagementsystem.leave.entiy.Role;
import com.leavemanagementsystem.leave.entiy.User;
import com.leavemanagementsystem.leave.repository.UserRepository;
import com.leavemanagementsystem.leave.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public LoginResponse login(LoginRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        Set<String> roles =
                user.getEmployee()
                        .getDepartment()
                        .getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet());

        String token = jwtService.generateToken(
                user.getUsername(),
                roles.stream().toList()
        );

        return LoginResponse.builder()
                .token(token)
                .username(user.getUsername())
                .employeeName(user.getEmployee().getFullName())
                .department(user.getEmployee().getDepartment().getName())
                .roles(roles)
                .build();
    }
}