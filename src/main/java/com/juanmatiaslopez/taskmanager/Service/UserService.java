package com.juanmatiaslopez.taskmanager.Service;

import com.juanmatiaslopez.taskmanager.DTO.ApiResponse;
import com.juanmatiaslopez.taskmanager.DTO.UserDTO;

import java.util.List;

public interface UserService {
    ApiResponse<List<UserDTO>> getAllUsers();
    ApiResponse<UserDTO> getProfile(String userName);
    ApiResponse<UserDTO> updateProfile(String userName, UserDTO userDTO);
}
