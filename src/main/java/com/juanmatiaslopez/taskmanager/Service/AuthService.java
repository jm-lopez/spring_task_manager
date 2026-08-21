package com.juanmatiaslopez.taskmanager.Service;

import com.juanmatiaslopez.taskmanager.DTO.ApiResponse;
import com.juanmatiaslopez.taskmanager.DTO.RegLoginRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    ApiResponse<?> register(RegLoginRequest regLoginRequest);
    ApiResponse<?> login(RegLoginRequest regLoginRequest, HttpServletRequest request);
}
