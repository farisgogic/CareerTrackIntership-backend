package com.team5.career_progression_app.dto;

import com.team5.career_progression_app.model.UserSkill;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSkillDTO {
    private Integer userId;
    private Integer skillId;
    private String skillName;
    private String level;

    public UserSkillDTO(UserSkill userSkill) {
        this.userId = userSkill.getUser().getId();
        this.skillId = userSkill.getSkill().getId();
        this.skillName = userSkill.getSkill().getName();
        this.level = userSkill.getLevel();
    }
}