package com.leavemanagementsystem.leave.entiy;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private LeaveType leaveType;

    private Integer totalDays;

    private Integer usedDays;

    private Integer remainingDays;

    private Integer carryForwardDays;

    private Integer year;
}