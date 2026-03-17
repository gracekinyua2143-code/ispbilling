package com.leavemanagementsystem.leave.security;



import com.leavemanagementsystem.leave.entiy.Employee;

public class RoleChecker {

    public static boolean hasRole(Employee employee, String roleName){

        return employee.getDepartment()
                .getRoles()
                .stream()
                .anyMatch(r -> r.getName().equals(roleName));

    }

}
