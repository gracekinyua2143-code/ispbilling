package com.leavemanagementsystem.leave.controler;



import com.leavemanagementsystem.leave.dto.ShiftAvailability;
import com.leavemanagementsystem.leave.service.LeaveCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class LeaveCalendarController {

    private final LeaveCalendarService leaveCalendarService;

    @GetMapping("/department/{departmentId}")
    public List<ShiftAvailability> getDepartmentCalendar(
            @PathVariable Long departmentId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ){

        return leaveCalendarService.getCalendar(
                departmentId,
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );
    }

}