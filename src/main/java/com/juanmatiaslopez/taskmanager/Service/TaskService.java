package com.juanmatiaslopez.taskmanager.Service;

import com.juanmatiaslopez.taskmanager.DTO.ApiResponse;
import com.juanmatiaslopez.taskmanager.DTO.TaskDTO;
import com.juanmatiaslopez.taskmanager.Enum.Category;

import java.util.List;

public interface TaskService {
    ApiResponse<TaskDTO> createTask(TaskDTO task, String userName);
    ApiResponse<List<TaskDTO>> getTaskByUserAndCategory(String userName, Category category);
    ApiResponse<TaskDTO> deleteTask(Long taskID, String UserName);
    ApiResponse<TaskDTO> toggleTaskStatus(Long taskID, String userName);
}
