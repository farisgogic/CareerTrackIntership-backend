package com.team5.career_progression_app.dto;

import com.team5.career_progression_app.model.Team;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
    private Integer id;
    private String name;
    private Integer leadId;

    public TeamDTO(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.leadId = team.getLead().getId();
    }
}