package com.team5.career_progression_app.dto;

import com.team5.career_progression_app.model.SkillType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillTypeDTO {
    private Integer id;
    private String name;

    public SkillTypeDTO(SkillType skillType) {
        this.id = skillType.getId();
        this.name = skillType.getName();
    }
}