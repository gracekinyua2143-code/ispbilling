package com.leavemanagementsystem.leave.repository;

import com.leavemanagementsystem.leave.entiy.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Optional<Employee> findByStaffNo(String staffNo);
    // ================================
    // ✅ RELIEVERS (same station, exclude roles)
    // ================================
    @Query("""
        SELECT DISTINCT e
        FROM Employee e
        JOIN e.department d
        JOIN d.roles r
        WHERE e.station.id = :stationId
        AND r.name NOT IN :excludedRoles
    """)
    List<Employee> findRelievers(Long stationId, Set<String> excludedRoles);


    // ================================
    // ✅ DM (same station)
    // ================================
    @Query("""
        SELECT DISTINCT e
        FROM Employee e
        JOIN e.department d
        JOIN d.roles r
        WHERE e.station.id = :stationId
        AND r.name = 'DM'
    """)
    List<Employee> findDMsInStation(Long stationId);


    // ================================
    // ✅ GLOBAL APPROVERS (any station)
    // ================================
    @Query("""
        SELECT DISTINCT e
        FROM Employee e
        JOIN e.department d
        JOIN d.roles r
        WHERE r.name IN ('HR','TM','CM','INTERNAL_AUDITOR')
    """)
    List<Employee> findGlobalApprovers();

}
