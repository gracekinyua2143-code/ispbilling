package com.leavemanagementsystem.leave.controler;

import com.leavemanagementsystem.leave.entiy.Department;
import com.leavemanagementsystem.leave.entiy.Employee;
import com.leavemanagementsystem.leave.entiy.LeaveType;
import com.leavemanagementsystem.leave.entiy.Station;
import com.leavemanagementsystem.leave.repository.DepartmentRepository;
import com.leavemanagementsystem.leave.repository.EmployeeRepository;
import com.leavemanagementsystem.leave.repository.LeaveTypeRepository;
import com.leavemanagementsystem.leave.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")
public class AdminController {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final StationRepository stationRepository;

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @GetMapping("/employees")
    public List<Employee> viewAll(){
        return employeeRepository.findAll();
    }

    @DeleteMapping("/employees/{id}")
    public void removeEmployee(@PathVariable Long id){
        employeeRepository.deleteById(id);
    }

    @PostMapping("/departments")
    public Department addDepartment(@RequestBody Department department){
        return departmentRepository.save(department);
    }

    @PostMapping("/leave-types")
    public LeaveType addLeaveType(@RequestBody LeaveType leaveType){
        return leaveTypeRepository.save(leaveType);
    }

    @PostMapping("/stations")
    public Station addStation(@RequestBody Station station){
        return stationRepository.save(station);
    }

}