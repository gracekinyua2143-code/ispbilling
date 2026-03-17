package com.leavemanagementsystem.leave.entiy;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private Boolean enabled = true;

    private Boolean firstLogin = true;

    @OneToOne
    private Employee employee;
}