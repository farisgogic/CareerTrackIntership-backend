package com.team5.career_progression_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_position")
public class UserPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "level")
    private Integer level;

    // Relationships

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    // toString method

    @Override
    public String toString() {
        return "UserPosition{" +
                "id=" + id +
                ", level=" + level +
                "userId=" + user.getId() +
                "positionId=" + position.getId() +
                '}';
    }
}
