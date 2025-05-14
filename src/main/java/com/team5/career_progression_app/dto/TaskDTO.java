package com.team5.career_progression_app.dto;

import com.team5.career_progression_app.model.Task;
import com.team5.career_progression_app.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskDTO {
    private Integer id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer assignedToId;
    private String assignedToName;
    private Integer templateId;
    private String templateName;

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus().name();
        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
        
        User assignedTo = task.getAssignedTo();
        if (assignedTo != null) {
            this.assignedToId = assignedTo.getId();
            this.assignedToName = assignedTo.getFirstName() + " " + assignedTo.getLastName();
        }
        
        if (task.getTemplate() != null) {
            this.templateId = task.getTemplate().getId();
            this.templateName = task.getTemplate().getName();
        }
    }
}