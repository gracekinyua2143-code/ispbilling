package com.leavemanagementsystem.leave.entiy;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private Boolean readStatus = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    private User user;
}
