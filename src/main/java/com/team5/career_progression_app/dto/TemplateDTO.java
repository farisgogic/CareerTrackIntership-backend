package com.team5.career_progression_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDTO {

    private int id;
    private String name;
    private String description;
    private String requirements;

}
