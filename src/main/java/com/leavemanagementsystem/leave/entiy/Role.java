package com.leavemanagementsystem.leave.entiy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    public Role() {} // default constructor

    @JsonCreator
    public Role(@JsonProperty("name") String name) {
        this.name = name;
    }

}