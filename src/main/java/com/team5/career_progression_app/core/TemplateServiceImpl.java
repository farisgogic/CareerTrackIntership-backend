package com.team5.career_progression_app.core;

import com.team5.career_progression_app.dto.TemplateDTO;
import com.team5.career_progression_app.exception.ResourceNotFoundException;
import com.team5.career_progression_app.model.Skill;
import com.team5.career_progression_app.model.TaskTemplate;
import com.team5.career_progression_app.model.TemplateSkill;
import com.team5.career_progression_app.repository.SkillRepository;
import com.team5.career_progression_app.repository.TaskTemplateRepository;
import com.team5.career_progression_app.repository.TemplateSkillRepository;
import com.team5.career_progression_app.service.TemplateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    private final TaskTemplateRepository taskTemplateRepository;
    private final SkillRepository skillRepository;
    private final TemplateSkillRepository templateSkillRepository;

    @Override
    public TemplateDTO createTemplate(TemplateDTO templateDTO) {
        TaskTemplate template = new TaskTemplate();
        template.setName(templateDTO.getName());
        template.setDescription(templateDTO.getDescription());
        template.setTaskRequirements(templateDTO.getRequirements());
        
        template = taskTemplateRepository.save(template);
        
        if (templateDTO.getSkillIds() != null && !templateDTO.getSkillIds().isEmpty()) {
            createTemplateSkills(template.getId(), templateDTO.getSkillIds());
        }
        
        return convertToDTO(template);
    }

    @Override
    public TemplateDTO updateTemplate(TemplateDTO templateDTO) {
        TaskTemplate template = taskTemplateRepository.findById(templateDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Template not found with id: " + templateDTO.getId()));

        template.setName(templateDTO.getName());
        template.setDescription(templateDTO.getDescription());
        template.setTaskRequirements(templateDTO.getRequirements());
        
        template = taskTemplateRepository.save(template);
        
        deleteTemplateSkills(template.getId());
        
        if (templateDTO.getSkillIds() != null && !templateDTO.getSkillIds().isEmpty()) {
            createTemplateSkills(template.getId(), templateDTO.getSkillIds());
        }
        
        return convertToDTO(template);
    }

    @Override
    public List<TemplateDTO> getAllTemplates() {
        List<TaskTemplate> templates = taskTemplateRepository.findAll();
        return templates.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TemplateDTO getTemplateById(int id) {
        TaskTemplate template = taskTemplateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found with id: " + id));
        return convertToDTO(template);
    }

    private void deleteTemplateSkills(int templateId) {
        templateSkillRepository.deleteByTemplateId(templateId);
    }

    private void createTemplateSkills(int templateId, List<Integer> skillIds) {
        TaskTemplate template = taskTemplateRepository.findById(templateId)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found with id: " + templateId));

        List<Skill> skills = skillRepository.findAllById(skillIds);
        
        if (skills.size() != skillIds.size()) {
            throw new ResourceNotFoundException("One or more skills not found");
        }

        List<TemplateSkill> templateSkills = skills.stream()
                .map(skill -> {
                    TemplateSkill templateSkill = new TemplateSkill();
                    templateSkill.setTemplate(template);
                    templateSkill.setSkill(skill);
                    templateSkill.setLevel("1"); // Default level
                    return templateSkill;
                })
                .collect(Collectors.toList());

        templateSkillRepository.saveAll(templateSkills);
    }

    private TemplateDTO convertToDTO(TaskTemplate template) {
        TemplateDTO dto = new TemplateDTO();
        dto.setId(template.getId());
        dto.setName(template.getName());
        dto.setDescription(template.getDescription());
        dto.setRequirements(template.getTaskRequirements());
        
        if (template.getTemplateSkills() != null) {
            List<Integer> skillIds = template.getTemplateSkills().stream()
                    .map(ts -> ts.getSkill().getId())
                    .collect(Collectors.toList());
            dto.setSkillIds(skillIds);
        }
        
        return dto;
    }
}
