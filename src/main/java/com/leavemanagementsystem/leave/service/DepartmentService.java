package com.leavemanagementsystem.leave.service;

import com.leavemanagementsystem.leave.entiy.Department;
import com.leavemanagementsystem.leave.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    // CREATE
    public Department save(Department d){
        return departmentRepository.save(d);
    }

    // READ
    public List<Department> all(){
        return departmentRepository.findAll();
    }

    public Department getById(Long id){
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }

    // UPDATE
    public Department update(Long id, Department updatedDepartment){

        Department department = getById(id);

        department.setName(updatedDepartment.getName());
        department.setRemittanceLimit(updatedDepartment.getRemittanceLimit());
        department.setStation(updatedDepartment.getStation());
        department.setRoles(updatedDepartment.getRoles());

        return departmentRepository.save(department);
    }

    // DELETE
    public void delete(Long id){
        departmentRepository.deleteById(id);
    }

}