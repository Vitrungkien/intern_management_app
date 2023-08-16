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

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

//    @OneToOne
//    @MapsId
//    private User user;

    public Intern(String name, String email, String position, Mentor mentor) {
        this.name = name;
        this.email = email;
        this.position = position;
        this.mentor = mentor;
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

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

}
