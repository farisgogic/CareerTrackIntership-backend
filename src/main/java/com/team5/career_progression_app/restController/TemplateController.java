package com.team5.career_progression_app.restController;

import com.team5.career_progression_app.dto.TemplateDTO;
import com.team5.career_progression_app.dto.TemplateSkillDTO;
import com.team5.career_progression_app.model.TaskTemplate;
import com.team5.career_progression_app.model.TemplateSkill;
import com.team5.career_progression_app.service.TemplateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.team5.career_progression_app.dto.AiGenerateTemplateRequestDTO;
import com.team5.career_progression_app.dto.AiGeneratedTemplateDTO;
import com.team5.career_progression_app.service.OpenAIService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/template")
@AllArgsConstructor
public class TemplateController {

    private final TemplateService templateService;
    private final OpenAIService openAIService;
    private final ObjectMapper objectMapper;

    @PostMapping("/generate-template-data")
    public AiGeneratedTemplateDTO generateTemplateData(@RequestBody AiGenerateTemplateRequestDTO request) throws Exception {
        String baseMessage = "You are an AI assistant for a career progression app. Your task is to generate a complete learning template based on keywords. " +
                "You MUST return a single, minified JSON object with no extra formatting or text outside the JSON. " +
                "The JSON object must have these exact keys: 'suggestedName' (string), 'suggestedDescription' (string, Markdown formatted), 'suggestedRequirements' (string, Markdown formatted), and 'suggestedSkills' (an array of strings). " +
                "The content should be in English. For 'suggestedDescription', include an overview, key topics, and links to resources. For 'suggestedRequirements', list prerequisites.";

        String aiResponse = openAIService.prompt(baseMessage, request.getKeywords());

        String cleanedJson = aiResponse.trim().replace("```json", "").replace("```", "");

        return objectMapper.readValue(cleanedJson, AiGeneratedTemplateDTO.class);
    }

    @PostMapping
    public TemplateDTO createTemplate(@Valid @RequestBody TemplateDTO templateDTO) {
        return templateService.createTemplate(templateDTO);
    }

    @PutMapping
    public TemplateDTO updateTemplate(@Valid @RequestBody TemplateDTO templateDTO) {
        return templateService.updateTemplate(templateDTO);
    }

    @GetMapping("/all")
    public List<TemplateDTO> getAllTemplates() {
        return templateService.getAllTemplates();
    }

    @GetMapping("/single/{id}")
    public TemplateDTO getTemplate(@PathVariable int id) {
        return templateService.getTemplateById(id);
    }

    private TemplateDTO convertToDTO(TaskTemplate template) {
        TemplateDTO dto = new TemplateDTO();
        dto.setId(template.getId());
        dto.setName(template.getName());
        dto.setDescription(template.getDescription());
        dto.setRequirements(template.getTaskRequirements());
        
        if (template.getTemplateSkills() != null) {
            List<TemplateSkillDTO> skillDTOs = template.getTemplateSkills().stream()
                    .map(TemplateSkillDTO::new)
                    .collect(Collectors.toList());
            dto.setSkills(skillDTOs);
            
            List<Integer> skillIds = template.getTemplateSkills().stream()
                    .map(ts -> ts.getSkill().getId())
                    .collect(Collectors.toList());
            dto.setSkillIds(skillIds);
        }
        
        return dto;
    }
}
