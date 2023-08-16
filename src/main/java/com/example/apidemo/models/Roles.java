package com.example.apidemo.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "role")
@Data
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Enumerated(EnumType.STRING)
    private ERole roleName;

    public Roles(ERole roleName) {
        this.roleName = roleName;
    }

    public Roles() {

    }
}
