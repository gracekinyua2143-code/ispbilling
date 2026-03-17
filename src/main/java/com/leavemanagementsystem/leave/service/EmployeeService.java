package com.leavemanagementsystem.leave.service;

import com.leavemanagementsystem.leave.entiy.Employee;
import com.leavemanagementsystem.leave.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    // CREATE
    public Employee addEmployee(Employee e){
        return employeeRepository.save(e);
    }

    // READ
    public List<Employee> allEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployee(Long id){
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    // UPDATE
    public Employee updateEmployee(Long id, Employee updatedEmployee){

        Employee employee = getEmployee(id);

        employee.setStaffNo(updatedEmployee.getStaffNo());
        employee.setFullName(updatedEmployee.getFullName());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setPhone(updatedEmployee.getPhone());
        employee.setDepartment(updatedEmployee.getDepartment());
        employee.setStation(updatedEmployee.getStation());
        employee.setActive(updatedEmployee.getActive());

        return employeeRepository.save(employee);
    }

    // DELETE
    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }

}