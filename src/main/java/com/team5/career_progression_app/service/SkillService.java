package com.team5.career_progression_app.service;

import com.team5.career_progression_app.dto.*;
import com.team5.career_progression_app.exception.*;
import com.team5.career_progression_app.model.*;
import com.team5.career_progression_app.repository.*;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SkillService {

    private final SkillRepository skillRepository;
    private final SkillTypeRepository skillTypeRepository;
    private final TemplateSkillRepository templateSkillRepository;
    private final UserSkillRepository userSkillRepository;
    private final TagRepository tagRepository;

    public SkillService(SkillRepository skillRepository, SkillTypeRepository skillTypeRepository,
            TemplateSkillRepository templateSkillRepository, UserSkillRepository userSkillRepository,
            TagRepository tagRepository) {
        this.skillRepository = skillRepository;
        this.skillTypeRepository = skillTypeRepository;
        this.templateSkillRepository = templateSkillRepository;
        this.userSkillRepository = userSkillRepository;
        this.tagRepository = tagRepository;
    }

    public List<SkillTypeDTO> getAllSkillTypes() {
        return skillTypeRepository.findAll().stream()
                .map(this::convertToSkillTypeDTO).toList();
    }

    public SkillTypeDTO createSkillType(SkillTypeDTO skillTypeDTO) {
        SkillType skillType = new SkillType();
        skillType.setName(skillTypeDTO.getName());
        SkillType saved = skillTypeRepository.save(skillType);
        return convertToSkillTypeDTO(saved);
    }

    public List<SkillDTO> getSkillsByType(Integer skillTypeId) {
        return skillRepository.findBySkillTypeId(skillTypeId).stream()
                .map(this::convertToSkillDTO).toList();
    }

    public SkillDTO createSkill(SkillDTO skillDTO) {
        SkillType skillType = skillTypeRepository.findById(skillDTO.getSkillTypeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Skill-Type with ID " + skillDTO.getSkillTypeId() + " not found"));

        if (skillRepository.existsByNameAndSkillTypeId(skillDTO.getName(), skillDTO.getSkillTypeId())) {
            throw new SkillAlreadyExistsException("Skill " + skillDTO.getName() + " already exists in this category");
        }

        Skill skill = new Skill();
        skill.setName(skillDTO.getName());
        skill.setSkillType(skillType);
        Skill saved = skillRepository.save(skill);
        return convertToSkillDTO(saved);
    }

    public List<TemplateSkillDTO> getSkillsForTemplate(Integer templateId) {
        return templateSkillRepository.findByTemplateId(templateId).stream()
                .map(this::convertToTemplateSkillDTO).toList();
    }

    public TemplateSkillDTO addSkillToTemplate(TemplateSkillDTO templateSkillDTO) {
        if (templateSkillRepository.existsByTemplateIdAndSkillId(
                templateSkillDTO.getTemplateId(), templateSkillDTO.getSkillId())) {
            throw new SkillAlreadyExistsException("Skill already added to this template");
        }

        TemplateSkill templateSkill = new TemplateSkill();
        templateSkill.setTemplate(new TaskTemplate(templateSkillDTO.getTemplateId()));
        templateSkill.setSkill(new Skill(templateSkillDTO.getSkillId()));
        templateSkill.setLevel(templateSkillDTO.getLevel());

        TemplateSkill saved = templateSkillRepository.save(templateSkill);
        return convertToTemplateSkillDTO(saved);
    }

    public List<UserSkillDTO> getSkillsForUser(Integer userId) {
        return userSkillRepository.findByUserId(userId).stream()
                .map(this::convertToUserSkillDTO).toList();
    }

    public UserSkillDTO addSkillToUser(UserSkillDTO userSkillDTO) {
        if (userSkillRepository.existsByUserIdAndSkillId(
                userSkillDTO.getUserId(), userSkillDTO.getSkillId())) {
            throw new SkillAlreadyExistsException("User already has this skill");
        }

        UserSkill userSkill = new UserSkill();
        userSkill.setUser(new User(userSkillDTO.getUserId()));
        userSkill.setSkill(new Skill(userSkillDTO.getSkillId()));
        userSkill.setLevel(userSkillDTO.getLevel());

        UserSkill saved = userSkillRepository.save(userSkill);
        return convertToUserSkillDTO(saved);
    }

    public TagDTO createTag(TagDTO tagDTO) {
        if (tagRepository.existsByName(tagDTO.getName())) {
            throw new ResourceNotFoundException("Tag already exists");
        }

        Tag tag = new Tag();
        tag.setName(tagDTO.getName());
        Tag saved = tagRepository.save(tag);
        return convertToTagDTO(saved);
    }

    public SkillDTO addTagToSkill(Integer skillId, Integer tagId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));

        if (skill.getTags().contains(tag)) {
            throw new ResourceNotFoundException("Tag already assigned to skill");
        }

        skill.getTags().add(tag);
        skillRepository.save(skill);

        return convertToSkillDTO(skill);
    }

    public List<TagDTO> getTagsForSkill(Integer skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));

        return skill.getTags().stream()
                .map(this::convertToTagDTO).toList();
    }

    public List<SkillDTO> findSimilarSkills(Integer skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));

        if (skill.getTags().isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> tagIds = skill.getTags().stream()
                .map(Tag::getId)
                .toList();

        return skillRepository.findAllByTagIdsAndExcludeSkill(tagIds, skillId).stream()
                .map(this::convertToSkillDTO)
                .toList();
    }

    public List<UserWithSkillsDTO> findUsersWithSimilarSkills(Integer skillId, String level) {
        List<SkillDTO> similarSkills = findSimilarSkills(skillId);
        if (similarSkills.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> similarSkillIds = similarSkills.stream()
                .map(SkillDTO::getId)
                .toList();

        List<UserSkill> userSkills;
        if (level == null || level.isEmpty()) {
            userSkills = userSkillRepository.findAllBySkillIds(similarSkillIds);
        } else {
            userSkills = userSkillRepository.findDetailedBySkillIdsAndLevel(
                    similarSkillIds, level);
        }

        Map<User, List<UserSkill>> usersWithSkills = userSkills.stream()
                .collect(Collectors.groupingBy(UserSkill::getUser));

        return usersWithSkills.entrySet().stream()
                .map(entry -> {
                    User user = entry.getKey();
                    List<UserSkillDTO> skillDTOs = entry.getValue().stream()
                            .map(this::convertToUserSkillDTO)
                            .toList();

                    return new UserWithSkillsDTO(
                            new UserDTO(user),
                            skillDTOs);
                })
                .toList();
    }

    private SkillTypeDTO convertToSkillTypeDTO(SkillType skillType) {
        return new SkillTypeDTO(skillType.getId(), skillType.getName());
    }

    private SkillDTO convertToSkillDTO(Skill skill) {
        return new SkillDTO(skill);
    }

    private TemplateSkillDTO convertToTemplateSkillDTO(TemplateSkill templateSkill) {
        return new TemplateSkillDTO(templateSkill);
    }

    private UserSkillDTO convertToUserSkillDTO(UserSkill userSkill) {
        return new UserSkillDTO(userSkill);
    }

    private TagDTO convertToTagDTO(Tag tag) {
        return new TagDTO(tag.getId(), tag.getName());
    }
}