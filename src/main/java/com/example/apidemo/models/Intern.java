package com.example.apidemo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "intern", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Intern {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "position")
    private String position;
    @Column(name = "mentor_id")
    private Long mentor_id;

    public Intern(String name, String email, String position, Long mentor_id) {
        this.name = name;
        this.email = email;
        this.position = position;
        this.mentor_id = mentor_id;
    }

    public Intern() {

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

    public Long getMentor_id() {
        return mentor_id;
    }

    public void setMentor_id(Long mentor_id) {
        this.mentor_id = mentor_id;
    }

//    @Override
//    public String toString() {
//        return "Intern{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", position='" + position + '\'' +
//                ", mentor_id=" + mentor_id +
//                '}';
//    }
}
