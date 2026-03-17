package com.leavemanagementsystem.leave.repository;

import com.leavemanagementsystem.leave.entiy.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveBalanceRepository
        extends JpaRepository<LeaveBalance,Long> {

    Optional<LeaveBalance> findByEmployeeIdAndLeaveTypeIdAndYear(
            Long employeeId,
            Long leaveTypeId,
            Integer year
    );
}