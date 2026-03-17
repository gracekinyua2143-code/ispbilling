package com.leavemanagementsystem.leave.repository;

import com.leavemanagementsystem.leave.entiy.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType,Long> {
}