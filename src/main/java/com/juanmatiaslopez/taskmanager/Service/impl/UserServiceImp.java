package com.juanmatiaslopez.taskmanager.Service.impl;

import com.juanmatiaslopez.taskmanager.DTO.ApiResponse;
import com.juanmatiaslopez.taskmanager.DTO.UserDTO;
import com.juanmatiaslopez.taskmanager.Exception.NotFoundException;
import com.juanmatiaslopez.taskmanager.Model.User;
import com.juanmatiaslopez.taskmanager.Repository.UserRepository;
import com.juanmatiaslopez.taskmanager.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiResponse<List<UserDTO>> getAllUsers() {
        return new ApiResponse<>(201, "Users returned", userRepository.findAll().stream().map(this::mapToDTO).toList());
    }

    @Override
    public ApiResponse<UserDTO> getProfile(String userName) {
        User user = userRepository.findUserByEmail(userName).orElseThrow(() -> new NotFoundException("User not found"));
        return new ApiResponse<>(200, "Profile found", mapToDTO(user));
    }

    @Override
    public ApiResponse<UserDTO> updateProfile(String userName, UserDTO userDTO) {
        User user = userRepository.findUserByEmail(userName).orElseThrow(() -> new NotFoundException("User not found"));
        if (userDTO.getEmail()!=null) user.setEmail(userDTO.getEmail());
        User updatedUser = userRepository.save(user);
        return new ApiResponse<>(200, "Profile updated", mapToDTO(updatedUser));
    }

    private UserDTO mapToDTO(User user) {
        UserDTO mappedUser = new UserDTO();
        mappedUser.setId(user.getId());
        mappedUser.setEmail(user.getEmail());
        mappedUser.setRole(user.getRole());
        return mappedUser;
    }
}
