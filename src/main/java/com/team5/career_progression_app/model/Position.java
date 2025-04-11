package com.team5.career_progression_app.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "position")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    // Relationships

    @OneToMany(mappedBy = "position")
    private List<UserPosition> userPositions;

    // Constructors

    public Position() {}

    public Position(String name) {
        this.name = name;
        userPositions = new ArrayList<>();
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserPosition> getUserPositions() {
        return userPositions;
    }

    public void setUserPositions(List<UserPosition> userPositions) {
        this.userPositions = userPositions;
    }

    // toString method

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
