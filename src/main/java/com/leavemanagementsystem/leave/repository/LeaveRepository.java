package com.leavemanagementsystem.leave.repository;

import com.leavemanagementsystem.leave.entiy.Leave;
import com.leavemanagementsystem.leave.entiy.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave,Long> {
    // employees already on leave for shift
    @Query("""
        SELECT COUNT(l)
        FROM Leave l
        WHERE l.employee.department.id = :departmentId
        AND l.shift = :shift
        AND l.status = 'APPROVED'
        AND (
            :startDate BETWEEN l.startDate AND l.endDate
            OR :endDate BETWEEN l.startDate AND l.endDate
        )
    """)
    long countDepartmentLeaves(Long departmentId,
                               Shift shift,
                               LocalDate startDate,
                               LocalDate endDate);


    // reliever already assigned
    @Query("""
        SELECT COUNT(l)
        FROM Leave l
        WHERE l.reliever.id = :relieverId
        AND l.status != 'REJECTED'
        AND (
            :startDate BETWEEN l.startDate AND l.endDate
            OR :endDate BETWEEN l.startDate AND l.endDate
        )
    """)
    long relieverConflict(Long relieverId,
                          LocalDate startDate,
                          LocalDate endDate);
    List<Leave> findByStatus(String status);
    @Query("""
SELECT COUNT(l)
FROM Leave l
WHERE l.employee.department.id = :departmentId
AND l.shift = :shift
AND l.status = 'APPROVED'
AND :date BETWEEN l.startDate AND l.endDate
""")
    long countLeavesForDate(Long departmentId, Shift shift, LocalDate date);
    @Query("""
SELECT COUNT(l)
FROM Leave l
WHERE l.employee.id = :employeeId
AND l.status != 'REJECTED'
AND (
    :startDate BETWEEN l.startDate AND l.endDate
    OR :endDate BETWEEN l.startDate AND l.endDate
    OR l.startDate BETWEEN :startDate AND :endDate
)
""")
    long countOverlappingLeaves(Long employeeId,
                                LocalDate startDate,
                                LocalDate endDate);
    @Query("""
SELECT COUNT(l)
FROM Leave l
WHERE l.employee.department.id = :departmentId
AND l.shift = :shift
AND l.status = 'APPROVED'
AND (
    :startDate BETWEEN l.startDate AND l.endDate
    OR :endDate BETWEEN l.startDate AND l.endDate
    OR l.startDate BETWEEN :startDate AND :endDate
)
""")
    long countShiftLeaves(Long departmentId,
                          String shift,
                          LocalDate startDate,
                          LocalDate endDate);
    @Query("""
SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END
FROM Leave l
WHERE l.reliever.id = :relieverId
AND l.status != 'REJECTED'
AND (
    :startDate BETWEEN l.startDate AND l.endDate
    OR :endDate BETWEEN l.startDate AND l.endDate
    OR l.startDate BETWEEN :startDate AND :endDate
)
""")
    boolean existsRelieverConflict(Long relieverId,
                                   LocalDate startDate,
                                   LocalDate endDate);

    List<Leave> findByEmployeeId(Long id);
}