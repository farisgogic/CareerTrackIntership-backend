package com.team5.career_progression_app.service;

import com.team5.career_progression_app.dto.*;
import com.team5.career_progression_app.exception.InvalidRequestException;
import com.team5.career_progression_app.exception.ResourceNotFoundException;
import com.team5.career_progression_app.exception.DuplicateAssignmentException;
import com.team5.career_progression_app.model.*;
import com.team5.career_progression_app.repository.NotificationRepository;
import com.team5.career_progression_app.repository.TaskRepository;
import com.team5.career_progression_app.repository.TaskTemplateRepository;
import com.team5.career_progression_app.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.team5.career_progression_app.model.Status;

import static com.team5.career_progression_app.model.Status.DONE;
import static com.team5.career_progression_app.model.Status.TODO;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskTemplateRepository taskTemplateRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

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

    public void assignTaskToUsers(TaskAssignmentRequest request) {
        Integer templateId = request.getTemplateId();
        String title = request.getTitle();
        String description = request.getDescription();
        List<Integer> userIds = request.getUserIds();

        TaskTemplate template = taskTemplateRepository.findById(templateId)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found with id: " + templateId));

        for (Integer userId : userIds) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

            Task existingTask = taskRepository.findTaskByFilter(
                    userId, templateId
            );

            if (existingTask != null) {
                existingTask.setTitle(title != null ? title : template.getName());
                existingTask.setDescription(description != null ? description : template.getDescription());
                if(existingTask.getStatus() == Status.DONE){
                    existingTask.setStatus(Status.TODO);
                }
                taskRepository.save(existingTask);
            } else {
                Task task = new Task();
                task.setTitle(title != null ? title : template.getName());
                task.setDescription(description != null ? description : template.getDescription());
                task.setTemplate(template);
                task.setAssignedTo(user);
                task.setStatus(Status.TODO);
                taskRepository.save(task);

                notificationService.notifyTaskReceived(user, task.getTitle());
            }
        }
    }

    public PaginatedResponse<TaskDTO> searchTasks(TaskSearchRequest request, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        
        Status status = request.getStatus() != null ?
                Status.valueOf(request.getStatus().toUpperCase()) : null;

        String searchQuery = request.getSearchQuery();
        String likePattern = searchQuery != null ? "%" + searchQuery.toLowerCase() + "%" : null;

        Page<Task> taskPage = taskRepository.searchTasks(
                request.getUserId(),
                status,
                request.getTemplateId(),
                searchQuery,
                likePattern,
                request.getTeamName(),
                request.getPositionId(),
                pageable
        );

        List<TaskDTO> tasks = taskPage.getContent()
                .stream()
                .map(TaskDTO::new)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                tasks,
                (int) taskPage.getTotalElements(),
                taskPage.getNumber(),
                taskPage.getSize(),
                taskPage.getTotalPages()
        );
    }

    public void deleteTask(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        User assignedUser = task.getAssignedTo();

        List<Task> tasksBefore = taskRepository.findByAssignedToId(assignedUser.getId());
        int totalBefore = tasksBefore.size();
        long completedBefore = tasksBefore.stream()
                .filter(t -> t.getStatus() == DONE)
                .count();
        double progressBefore = totalBefore > 0 ? (double) completedBefore / totalBefore : 0.0;

        taskRepository.delete(task);

        List<Task> tasksAfter = taskRepository.findByAssignedToId(assignedUser.getId());
        int totalAfter = tasksAfter.size();
        long completedAfter = tasksAfter.stream()
                .filter(t -> t.getStatus() == DONE)
                .count();
        double progressAfter = totalAfter > 0 ? (double) completedAfter / totalAfter : 0.0;

        notificationService.notifyTaskDeleted(
            assignedUser,
            task.getTitle(),
            String.format("%.1f%%", progressAfter * 100)
        );
    }

    public TaskDTO getTaskDetails(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        return new TaskDTO(task);
    }

    public TaskDTO updateTask(Integer taskId, TaskUpdateRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        if (request.getTemplateId() == null || request.getDescription() == null || request.getDescription().trim().isEmpty()) {
            throw new InvalidRequestException("Template ID and description are required");
        }

        TaskTemplate template = taskTemplateRepository.findById(request.getTemplateId())
                .orElseThrow(() -> new ResourceNotFoundException("Template not found with id: " + request.getTemplateId()));

        Task existingTask = taskRepository.findTaskByFilter(task.getAssignedTo().getId(), request.getTemplateId());
        
        if (existingTask != null && !existingTask.getId().equals(taskId)) {
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(request.getDescription());
            if (existingTask.getStatus() == Status.DONE) {
                existingTask.setStatus(Status.TODO);
            }
            Task updatedTask = taskRepository.save(existingTask);
            
            taskRepository.delete(task);
            
            return new TaskDTO(updatedTask);
        } else {
            task.setTemplate(template);
            task.setDescription(request.getDescription());
            Task updatedTask = taskRepository.save(task);
            return new TaskDTO(updatedTask);
        }
    }
}