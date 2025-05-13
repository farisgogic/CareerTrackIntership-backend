package com.team5.career_progression_app.restController;

import com.team5.career_progression_app.model.Task;
import com.team5.career_progression_app.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasksk")
public class TaskTemplateController {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskTemplateController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private final Map<Long, String> tasks = new HashMap<>();

    @GetMapping
    public List<String> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return this.taskRepository.save(task);
    }

    @GetMapping("/{id}")
    public String getTask(@PathVariable Long id){
                          return tasks.getOrDefault(id, "Task not found");
}

@PutMapping("/{id}")
public String updateTask(@PathVariable Long id, @RequestBody String task) {
    if (!tasks.containsKey(id)) return "Task not found";
    tasks.put(id, task);
    return "Task updated";
}

@DeleteMapping("/{id}")
public String deleteTask(@PathVariable Long id) {
    if (tasks.remove(id) == null) return "Task not found";
    return "Task deleted";
}
}
