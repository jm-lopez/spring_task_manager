package com.juanmatiaslopez.taskmanager.Service.impl;

import com.juanmatiaslopez.taskmanager.DTO.ApiResponse;
import com.juanmatiaslopez.taskmanager.DTO.TaskDTO;
import com.juanmatiaslopez.taskmanager.Enum.Category;
import com.juanmatiaslopez.taskmanager.Exception.BadRequestException;
import com.juanmatiaslopez.taskmanager.Exception.NotFoundException;
import com.juanmatiaslopez.taskmanager.Model.Task;
import com.juanmatiaslopez.taskmanager.Model.User;
import com.juanmatiaslopez.taskmanager.Repository.TaskRepository;
import com.juanmatiaslopez.taskmanager.Repository.UserRepository;
import com.juanmatiaslopez.taskmanager.Service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TaskServiceImp implements TaskService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public ApiResponse<TaskDTO> createTask(TaskDTO task, String userName) {
        User user = userRepository.findUserByEmail(userName).orElseThrow(() -> new NotFoundException("User not found"));
        Task newTask = new Task();
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        newTask.setCategory(task.getCategory() != null ? task.getCategory() : Category.PERSONAL );
        newTask.setUser(user);

        Task savedTask = taskRepository.save(newTask);
        return new ApiResponse<>(200, "Task created", mapToDTO(savedTask));
    }

    @Override
    public ApiResponse<List<TaskDTO>> getTaskByUserAndCategory(String userName, Category category) {
        User user = userRepository.findUserByEmail(userName).orElseThrow(() -> new NotFoundException("User not found"));
        List<Task> list;
        if (category != null ) list = taskRepository.findByUserAndCategory(user, category);
        else list = taskRepository.findByUser(user);
        List<TaskDTO> response = list.stream().map(this::mapToDTO).toList();
        return new ApiResponse<>(200, "Task found", response);
    }

    @Override
    public ApiResponse<TaskDTO> deleteTask(Long taskID, String userName) {
        if (!taskRepository.existsById(taskID)) throw new NotFoundException("Task not found");
        User user = userRepository.findUserByEmail(userName).orElseThrow(() -> new NotFoundException("User not found"));
        if (!user.getEmail().equals(userName)) throw new BadRequestException("You need to own the task to modify it");
        taskRepository.deleteById(taskID);
        return new ApiResponse<>(200, "Task deleted", null);
    }

    @Override
    public ApiResponse<TaskDTO> toggleTaskStatus(Long taskID, String userName) {
        User user = userRepository.findUserByEmail(userName).orElseThrow(() -> new NotFoundException("User not found"));
        if (!taskRepository.existsById(taskID)) throw new NotFoundException("Task not found");
        Task task = taskRepository.findById(taskID).orElseThrow(() -> new NotFoundException("Task not found"));
        if (!user.getEmail().equals(userName)) throw new BadRequestException("You need to own the task to modify it");
        task.setCompleted(!task.isCompleted());
        Task savedTask = taskRepository.save(task);
        return new ApiResponse<>(200, "Task modified", mapToDTO(savedTask));
    }

    private TaskDTO mapToDTO (Task task) {
        TaskDTO mappedTask = new TaskDTO();
        mappedTask.setId(task.getId());
        mappedTask.setTitle(task.getTitle());
        mappedTask.setDescription(task.getDescription());
        mappedTask.setCategory(task.getCategory());
        mappedTask.setCompleted(task.isCompleted());
        return mappedTask;
    }
}
