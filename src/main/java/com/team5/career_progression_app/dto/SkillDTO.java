package com.team5.career_progression_app.dto;

import com.team5.career_progression_app.model.Skill;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SkillDTO {
    private Integer id;
    private String name;
    private Integer skillTypeId;
    private String skillTypeName;

    public SkillDTO(Skill skill) {
        this.id = skill.getId();
        this.name = skill.getName();
        if (skill.getSkillType() != null) {
            this.skillTypeId = skill.getSkillType().getId();
            this.skillTypeName = skill.getSkillType().getName();
        }
    }
}