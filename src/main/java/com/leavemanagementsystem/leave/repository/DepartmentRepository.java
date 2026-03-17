package com.leavemanagementsystem.leave.repository;

import com.leavemanagementsystem.leave.entiy.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    Optional<Department> findByName(String systemAdmin);
}