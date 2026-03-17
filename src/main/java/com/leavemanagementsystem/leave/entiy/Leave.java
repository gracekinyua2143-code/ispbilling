package com.leavemanagementsystem.leave.entiy;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Entity
@Table(name = "leaves")
@Data
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private LeaveType leaveType;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate reportingDate;

    @Enumerated(EnumType.STRING)
    private Shift shift;

    @ManyToOne
    private Employee reliever;

    @ManyToOne
    private Employee approver;

    private String status;

}