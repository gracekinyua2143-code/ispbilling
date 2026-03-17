package com.leavemanagementsystem.leave.entiy;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String staffNo;

    private String fullName;

    private String email;

    private String phone;

    private Boolean active = true;

    @ManyToOne
    private Department department;

    @ManyToOne
    private Station station;
}