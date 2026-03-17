package com.leavemanagementsystem.leave.service;

import com.leavemanagementsystem.leave.dto.RegisterRequest;
import com.leavemanagementsystem.leave.dto.ResetPasswordRequest;
import com.leavemanagementsystem.leave.entiy.Employee;
import com.leavemanagementsystem.leave.entiy.User;
import com.leavemanagementsystem.leave.repository.EmployeeRepository;
import com.leavemanagementsystem.leave.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // CREATE ACCOUNT
    public User register(RegisterRequest request){

        // 1 check employee exists
        Employee employee = employeeRepository.findByStaffNo(request.getStaffNo())
                .orElseThrow(() -> new RuntimeException("Employee not registered in system"));

        // 2 check employee active
        if(!employee.getActive()){
            throw new RuntimeException("Employee account is deactivated");
        }

        // 3 check employee already has account
        userRepository.findByEmployeeId(employee.getId())
                .ifPresent(u -> {
                    throw new RuntimeException("Account already exists for this employee");
                });

        // 4 check username unique
        userRepository.findByUsername(request.getUsername())
                .ifPresent(u -> {
                    throw new RuntimeException("Username already taken");
                });

        // 5 create account
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmployee(employee);

        return userRepository.save(user);
    }


    // RESET PASSWORD USING STAFF NO
    public void resetPassword(ResetPasswordRequest request){

        Employee employee = employeeRepository.findByStaffNo(request.getStaffNo())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        User user = userRepository.findByEmployeeId(employee.getId())
                .orElseThrow(() -> new RuntimeException("User account not found"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }

}