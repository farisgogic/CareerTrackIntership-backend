package com.team5.career_progression_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilterCountDTO {
    private String filter;
    private Integer count;
}
