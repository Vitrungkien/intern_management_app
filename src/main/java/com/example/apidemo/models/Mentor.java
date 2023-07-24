package com.example.apidemo.models;


import jakarta.persistence.*;

@Entity
@Table(name = "mentor", uniqueConstraints = @UniqueConstraint(columnNames = "email"))

public class Mentor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "position")
    private String position;

    public Mentor(String name, String email, String position) {
        this.name = name;
        this.email = email;
        this.position = position;
    }

    public Mentor() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}

