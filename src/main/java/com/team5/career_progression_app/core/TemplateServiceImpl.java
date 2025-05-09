package com.team5.career_progression_app.core;

import com.team5.career_progression_app.dto.TemplateDTO;
import com.team5.career_progression_app.model.TaskTemplate;
import com.team5.career_progression_app.repository.TaskTemplateRepository;
import com.team5.career_progression_app.service.TemplateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    private final TaskTemplateRepository taskTemplateRepository;

    @Override
    public TemplateDTO createTemplate(TemplateDTO template) {
        TaskTemplate taskTemplate = new TaskTemplate();
        taskTemplate.setName(template.getName());
        taskTemplate.setDescription(template.getDescription());
        taskTemplate.setTaskRequirements(template.getRequirements());
        taskTemplateRepository.save(taskTemplate);
        return template;
    }

    @Override
    public TemplateDTO updateTemplate(TemplateDTO template) {
        TaskTemplate taskTemplate =taskTemplateRepository.findById(template.getId()).get();
        taskTemplate.setName(template.getName());
        taskTemplate.setDescription(template.getDescription());
        taskTemplate.setTaskRequirements(template.getRequirements());
        taskTemplateRepository.save(taskTemplate);
        return template;
    }

    @Override
    public List<TemplateDTO> getAllTemplates() {
        List<TaskTemplate> taskTemplates = taskTemplateRepository.findAll();
        return taskTemplates.stream()
                .map(template -> new TemplateDTO(template.getId(), template.getName(), template.getDescription(), template.getTaskRequirements())).toList();
    }

    @Override
    public TemplateDTO getTemplateById(int id) {
        TaskTemplate taskTemplate=taskTemplateRepository.getById(id);
        return new TemplateDTO(id, taskTemplate.getName(), taskTemplate.getDescription(), taskTemplate.getTaskRequirements());
    }
}
