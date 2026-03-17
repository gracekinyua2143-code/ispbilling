package com.leavemanagementsystem.leave.config;



import com.leavemanagementsystem.leave.entiy.*;
import com.leavemanagementsystem.leave.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;
    private final StationRepository stationRepository;
    @Override
    public void run(String... args) {

        createRole("SUPER_ADMIN");
        createRole("ADMIN");
        createRole("HR");
        createRole("TM");
        createRole("CM");
        createRole("INTERNAL_AUDITOR");
        createRole("DM");
        createRole("EMPLOYEE");
        Role superAdminRole = roleRepository.findByName("SUPER_ADMIN")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("SUPER_ADMIN");
                    return roleRepository.save(role);
                });

        createSuperAdmin();
    }

    private void createRole(String name){

        roleRepository.findByName(name)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName(name);
                    return roleRepository.save(role);
                });
    }



    private void createSuperAdmin(){

        if(userRepository.findByUsername("superadmin").isPresent()){
            return;
        }

        // 1. Create or get Station
        Station station = stationRepository.findByName("HEADQUARTERS")
                .orElseGet(() -> {
                    Station s = new Station();
                    s.setName("HEADQUARTERS");
                    return stationRepository.save(s);
                });

        // 2. Create or get Role
        Role role = roleRepository.findByName("SUPER_ADMIN")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName("SUPER_ADMIN");
                    return roleRepository.save(r);
                });

        // 3. Create or get Department
        Department department = departmentRepository.findByName("SYSTEM_ADMIN")
                .orElseGet(() -> {
                    Department d = new Department();
                    d.setName("SYSTEM_ADMIN");
                    d.setRemittanceLimit(999);
                    d.setStation(station);
                    d.setRoles(Set.of(role));
                    return departmentRepository.save(d);
                });

        // 4. Create Employee
        Employee employee = new Employee();
        employee.setStaffNo("SYS001");
        employee.setFullName("System Super Admin");
        employee.setEmail("admin@system.com");
        employee.setPhone("0000000000");
        employee.setActive(true);

        employee.setDepartment(department);
       employee.setStation(station);

        employeeRepository.save(employee);

        // 5. Create User Login
        User user = new User();
        user.setUsername("superadmin");
        user.setPassword(passwordEncoder.encode("admin123"));
        user.setEmployee(employee);

        userRepository.save(user);
    }
}