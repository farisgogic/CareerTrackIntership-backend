package com.team5.career_progression_app.restController;

import com.team5.career_progression_app.dto.TemplateDTO;
import com.team5.career_progression_app.service.TemplateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/template")
@AllArgsConstructor
public class TemplateController {

    private final TemplateService templateService;


    @PostMapping
    public TemplateDTO createTemplate(@Valid @RequestBody TemplateDTO templateDTO) {
        return this.templateService.createTemplate(templateDTO);
    }

    @PutMapping
    public TemplateDTO updateTemplate(@Valid @RequestBody TemplateDTO templateDTO) {
        return this.templateService.updateTemplate(templateDTO);
    }

    @GetMapping("/all")
    public List<TemplateDTO> getAllTemplates() {
        return this.templateService.getAllTemplates();
    }
    @GetMapping("/single/{id}")
    public TemplateDTO getTask(@PathVariable int id){
        return this.templateService.getTemplateById(id);

    }

}
