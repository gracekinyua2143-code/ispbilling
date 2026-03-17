package com.leavemanagementsystem.leave.service;

import com.leavemanagementsystem.leave.entiy.Department;
import com.leavemanagementsystem.leave.repository.LeaveRepository;
import com.leavemanagementsystem.leave.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ShiftAvailabilityService {

    private final LeaveRepository leaveRepository;
    private final DepartmentRepository departmentRepository;

    public boolean isShiftAvailable(Long departmentId,
                                    String shift,
                                    LocalDate startDate,
                                    LocalDate endDate){

        Department department = departmentRepository
                .findById(departmentId)
                .orElseThrow();

        // Max employees allowed on leave for this department
        int maxAllowed = department.getRemittanceLimit();

        long booked = leaveRepository.countShiftLeaves(
                departmentId,
                shift,
                startDate,
                endDate
        );

        return booked < maxAllowed;
    }
}