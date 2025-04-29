package com.team5.career_progression_app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "UserPosition")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "level")
    private Integer level;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

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
