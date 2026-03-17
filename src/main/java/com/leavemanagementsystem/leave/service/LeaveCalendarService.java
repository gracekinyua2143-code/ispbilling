package com.leavemanagementsystem.leave.service;

import com.leavemanagementsystem.leave.dto.ShiftAvailability;
import com.leavemanagementsystem.leave.entiy.Department;
import com.leavemanagementsystem.leave.entiy.Shift;
import com.leavemanagementsystem.leave.repository.DepartmentRepository;
import com.leavemanagementsystem.leave.repository.LeaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveCalendarService {

    private final LeaveRepository leaveRepository;
    private final DepartmentRepository departmentRepository;

    public List<ShiftAvailability> getCalendar(Long departmentId,
                                               LocalDate startDate,
                                               LocalDate endDate){

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        int capacity = department.getRemittanceLimit();

        List<ShiftAvailability> calendar = new ArrayList<>();

        LocalDate date = startDate;

        while(!date.isAfter(endDate)){

            long shiftAUsed =
                    leaveRepository.countLeavesForDate(
                            departmentId,
                            Shift.SHIFT_A,
                            date
                    );

            long shiftBUsed =
                    leaveRepository.countLeavesForDate(
                            departmentId,
                            Shift.SHIFT_B,
                            date
                    );

            calendar.add(
                    ShiftAvailability.builder()
                            .date(date)

                            .shiftACapacity(capacity)
                            .shiftAUsed((int) shiftAUsed)
                            .shiftARemaining(capacity - (int) shiftAUsed)

                            .shiftBCapacity(capacity)
                            .shiftBUsed((int) shiftBUsed)
                            .shiftBRemaining(capacity - (int) shiftBUsed)

                            .build()
            );

            date = date.plusDays(1);
        }

        return calendar;
    }
}