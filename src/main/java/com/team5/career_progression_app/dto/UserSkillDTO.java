package com.team5.career_progression_app.dto;

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
}