package com.leavemanagementsystem.leave.entiy;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer remittanceLimit;

    @ManyToOne
    private Station station;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "department_roles",
            joinColumns = @JoinColumn(name="department_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles;
}