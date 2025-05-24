package com.team5.career_progression_app.restController;

import com.team5.career_progression_app.dto.ApiResponse;
import com.team5.career_progression_app.dto.PaginatedResponse;
import com.team5.career_progression_app.dto.TaskDTO;
import com.team5.career_progression_app.model.Status;
import com.team5.career_progression_app.service.TaskService;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/assigned-to/{userId}")
    public ApiResponse<PaginatedResponse<TaskDTO>> getTasksAssignedToUser(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        PaginatedResponse<TaskDTO> response = taskService.getTasksByUserId(userId, page, size);
        return new ApiResponse<>(true, "Tasks fetched successfully", response);
    }


    @GetMapping("/user/{userId}/all")
    public ApiResponse<List<TaskDTO>> getAllTasksForUser(@PathVariable Integer userId) {
        List<TaskDTO> tasks = taskService.getAllTasksForUser(userId);
        return new ApiResponse<>(true, "All tasks fetched successfully", tasks);
    }

    @GetMapping("/user/{userId}/status/{status}")
    public ApiResponse<List<TaskDTO>> getTasksForUserByStatus(
            @PathVariable Integer userId,
            @PathVariable String status) {
        
        Status taskStatus = Status.valueOf(status.toUpperCase());
        List<TaskDTO> tasks = taskService.getTasksForUserByStatus(userId, taskStatus);
        return new ApiResponse<>(true, "Tasks by status fetched successfully", tasks);
    }
}