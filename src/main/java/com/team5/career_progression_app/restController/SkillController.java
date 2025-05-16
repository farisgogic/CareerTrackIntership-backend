package com.team5.career_progression_app.restController;

import com.team5.career_progression_app.dto.*;
import com.team5.career_progression_app.service.SkillService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping("/types")
    public List<SkillTypeDTO> getAllSkillTypes() {
        return skillService.getAllSkillTypes();
    }

    @PostMapping("/types")
    public SkillTypeDTO createSkillType(@RequestBody SkillTypeDTO skillTypeDTO) {
        return skillService.createSkillType(skillTypeDTO);
    }

    @GetMapping("/types/{skillTypeId}/skills")
    public List<SkillDTO> getSkillsByType(@PathVariable Integer skillTypeId) {
        return skillService.getSkillsByType(skillTypeId);
    }

    @PostMapping
    public SkillDTO createSkill(@RequestBody SkillDTO skillDTO) {
        return skillService.createSkill(skillDTO);
    }

    @GetMapping("/{skillId}/tags")
    public List<TagDTO> getTagsForSkill(@PathVariable Integer skillId) {
        return skillService.getTagsForSkill(skillId);
    }

    @PostMapping("/{skillId}/tags/{tagId}")
    public SkillDTO addTagToSkill(
            @PathVariable Integer skillId,
            @PathVariable Integer tagId) {
        return skillService.addTagToSkill(skillId, tagId);
    }

    @GetMapping("/{skillId}/similar-skills")
    public List<SkillDTO> findSimilarSkills(@PathVariable Integer skillId) {
        return skillService.findSimilarSkills(skillId);
    }

    @GetMapping("/{skillId}/similar-users")
    public List<UserWithSkillsDTO> findUsersWithSimilarSkills(
            @PathVariable Integer skillId,
            @RequestParam(required = false, defaultValue = "1") String level) {
        return skillService.findUsersWithSimilarSkills(skillId, level);
    }

    @PostMapping("/tags")
    public TagDTO createTag(@RequestBody TagDTO tagDTO) {
        return skillService.createTag(tagDTO);
    }

    @GetMapping("/templates/{templateId}/skills")
    public List<TemplateSkillDTO> getSkillsForTemplate(@PathVariable Integer templateId) {
        return skillService.getSkillsForTemplate(templateId);
    }

    @PostMapping("/templates/skills")
    public TemplateSkillDTO addSkillToTemplate(@RequestBody TemplateSkillDTO templateSkillDTO) {
        return skillService.addSkillToTemplate(templateSkillDTO);
    }

    @GetMapping("/users/{userId}/skills")
    public List<UserSkillDTO> getSkillsForUser(@PathVariable Integer userId) {
        return skillService.getSkillsForUser(userId);
    }

    @PostMapping("/users/skills")
    public UserSkillDTO addSkillToUser(@RequestBody UserSkillDTO userSkillDTO) {
        return skillService.addSkillToUser(userSkillDTO);
    }
}
