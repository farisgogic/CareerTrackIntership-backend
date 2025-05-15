package com.team5.career_progression_app.dto;

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
    
    public SkillDTO(Integer id, String name, Integer skillTypeId, String skillTypeName) {
        this.id = id;
        this.name = name;
        this.skillTypeId = skillTypeId;
        this.skillTypeName = skillTypeName;
    }
}