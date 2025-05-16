package com.team5.career_progression_app.dto;

import com.team5.career_progression_app.model.TemplateSkill;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemplateSkillDTO {
    private Integer templateId;
    private Integer skillId;
    private String skillName;
    private String level;

    public TemplateSkillDTO(TemplateSkill templateSkill) {
        this.templateId = templateSkill.getTemplate().getId();
        this.skillId = templateSkill.getSkill().getId();
        this.skillName = templateSkill.getSkill().getName();
        this.level = templateSkill.getLevel();
    }
}