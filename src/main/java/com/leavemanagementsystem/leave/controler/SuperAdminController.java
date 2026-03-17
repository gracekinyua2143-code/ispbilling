package com.leavemanagementsystem.leave.controler;

import com.leavemanagementsystem.leave.entiy.*;
import com.leavemanagementsystem.leave.repository.*;
import com.leavemanagementsystem.leave.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/superadmin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class SuperAdminController {

    private final DepartmentRepository departmentRepository;
    private final StationRepository stationRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final EmployeeRepository employeeRepository;

    private final RemittanceService remittanceService;
    private final StationService stationService;
    private final RoleService roleService;

    // ✅ ADD THESE SERVICES
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    private final LeaveTypeService leaveTypeService;

    // ========================= DEPARTMENT =========================

    @GetMapping("/departments")
    public List<Department> getDepartments(){
        return departmentService.all();
    }

    @PostMapping("/departments")
    public Department addDepartment(@RequestBody Department department){
        return departmentService.save(department);
    }

    @PutMapping("/departments/{id}")
    public Department updateDepartment(@PathVariable Long id,
                                       @RequestBody Department department){
        return departmentService.update(id, department);
    }

    @DeleteMapping("/departments/{id}")
    public void deleteDepartment(@PathVariable Long id){
        departmentService.delete(id);
    }

    @PutMapping("/departments/{id}/remittance")
    public Department setRemittance(@PathVariable Long id,
                                    @RequestParam Integer remittance){
        return remittanceService.setRemittance(id, remittance);
    }

    // ========================= STATION =========================

    @PostMapping("/stations")
    public Station addStation(@RequestBody Station station){
        return stationService.createStation(station);
    }

    @GetMapping("/stations")
    public List<Station> getStations(){
        return stationService.getStations();
    }

    @GetMapping("/stations/{id}")
    public Station getStation(@PathVariable Long id){
        return stationService.getById(id);
    }

    @PutMapping("/stations/{id}")
    public Station updateStation(@PathVariable Long id,
                                 @RequestBody Station updatedStation){
        return stationService.updateStation(id, updatedStation);
    }

    @DeleteMapping("/stations/{id}")
    public void deleteStation(@PathVariable Long id){
        stationService.deleteStation(id);
    }

    // ========================= LEAVE TYPE =========================

    @PostMapping("/leave-types")
    public LeaveType addLeaveType(@RequestBody LeaveType leaveType){
        return leaveTypeService.create(leaveType);
    }

    @GetMapping("/leave-types")
    public List<LeaveType> getLeaveTypes(){
        return leaveTypeService.getAll();
    }

    @PutMapping("/leave-types/{id}")
    public LeaveType updateLeaveType(@PathVariable Long id,
                                     @RequestBody LeaveType leaveType){
        return leaveTypeService.update(id, leaveType);
    }

    @DeleteMapping("/leave-types/{id}")
    public void deleteLeaveType(@PathVariable Long id){
        leaveTypeService.delete(id);
    }

    // ========================= EMPLOYEE =========================

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/employees")
    public List<Employee> getAllStaff(){
        return employeeService.allEmployees();
    }

    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@PathVariable Long id,
                                   @RequestBody Employee employee){
        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
    }

    // ========================= ROLES =========================

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.all();
    }
}