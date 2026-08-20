package com.juanmatiaslopez.taskmanager.Controller;

import com.juanmatiaslopez.taskmanager.DTO.ApiResponse;
import com.juanmatiaslopez.taskmanager.DTO.TaskDTO;
import com.juanmatiaslopez.taskmanager.Enum.Category;
import com.juanmatiaslopez.taskmanager.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(@RequestBody TaskDTO taskDTO, Authentication auth) {
        ApiResponse<TaskDTO> response = taskService.createTask(taskDTO, auth.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskDTO>>> getTasksByUserAndCategory(@RequestParam(required = false)Category category, Authentication auth) {
        return ResponseEntity.ok(taskService.getTaskByUserAndCategory(auth.getName(), category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteTask(@PathVariable Long id, Authentication auth) {
        return ResponseEntity.ok(taskService.deleteTask(id, auth.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDTO>> toggleTask(@PathVariable Long id, Authentication auth) {
        return ResponseEntity.ok(taskService.toggleTaskStatus(id, auth.getName()));
    }
}
