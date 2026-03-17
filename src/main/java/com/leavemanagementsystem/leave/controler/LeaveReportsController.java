package com.leavemanagementsystem.leave.controler;

import com.leavemanagementsystem.leave.dto.*;
import com.leavemanagementsystem.leave.service.LeaveReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class LeaveReportsController {

    private final LeaveReportsService reportsService;

    // Only SUPER_ADMIN or ADMIN
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @GetMapping("/department/{id}")
    public List<DepartmentLeaveReport> departmentReport(@PathVariable Long id){
        return reportsService.departmentReport(id);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @GetMapping("/station/{id}")
    public List<StationLeaveReport> stationReport(@PathVariable Long id){
        return reportsService.stationReport(id);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @GetMapping("/monthly")
    public List<MonthlyLeaveReport> monthlyReport(@RequestParam int year,
                                                  @RequestParam int month){
        return reportsService.monthlyReport(year, month);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @GetMapping("/employee/{id}")
    public List<EmployeeLeaveHistory> employeeHistory(@PathVariable Long id){
        return reportsService.employeeHistory(id);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @GetMapping("/leave-balance")
    public List<LeaveBalanceReport> leaveBalanceReport(){
        return reportsService.leaveBalanceReport();
    }

}