package com.team5.career_progression_app.service;

import com.team5.career_progression_app.dto.PaginatedResponse;
import com.team5.career_progression_app.dto.TaskDTO;
import com.team5.career_progression_app.model.Status;
import com.team5.career_progression_app.model.Task;
import com.team5.career_progression_app.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public PaginatedResponse<TaskDTO> getTasksByUserId(Integer userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> taskPage = taskRepository.findByAssignedToId(userId, pageable);
        
        List<TaskDTO> tasks = taskPage.getContent()
                .stream()
                .map(TaskDTO::new)
                .collect(Collectors.toList());
                
        return new PaginatedResponse<>(tasks, (int) taskPage.getTotalElements(), taskPage.getNumber(), taskPage.getSize(), taskPage.getTotalPages());
    }

    public List<TaskDTO> getAllTasksForUser(Integer userId) {
        return taskRepository.findByAssignedToId(userId)
                .stream()
                .map(TaskDTO::new)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksForUserByStatus(Integer userId, Status status) {
        return taskRepository.findByAssignedToIdAndStatus(userId, status)
                .stream()
                .map(TaskDTO::new)
                .collect(Collectors.toList());
    }
}