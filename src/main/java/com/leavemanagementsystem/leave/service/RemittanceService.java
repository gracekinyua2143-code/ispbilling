package com.leavemanagementsystem.leave.service;

import com.leavemanagementsystem.leave.entiy.Department;
import com.leavemanagementsystem.leave.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemittanceService {

    private final DepartmentRepository departmentRepository;

    public Department setRemittance(Long departmentId, Integer remittance){

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        department.setRemittanceLimit(remittance);

        return departmentRepository.save(department);
    }
}