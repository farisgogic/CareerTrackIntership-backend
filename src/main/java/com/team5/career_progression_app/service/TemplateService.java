package com.team5.career_progression_app.service;

import com.team5.career_progression_app.dto.TemplateDTO;

import java.util.List;

public interface TemplateService {
    TemplateDTO createTemplate(TemplateDTO templateDTO);
    TemplateDTO updateTemplate(TemplateDTO templateDTO);
    List<TemplateDTO> getAllTemplates();
    TemplateDTO getTemplateById(int id);
}
