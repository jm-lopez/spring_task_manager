package com.juanmatiaslopez.taskmanager.Controller;

import com.juanmatiaslopez.taskmanager.DTO.ApiResponse;
import com.juanmatiaslopez.taskmanager.DTO.RegLoginRequest;
import com.juanmatiaslopez.taskmanager.Service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody RegLoginRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody RegLoginRequest regRequest, HttpServletRequest request){
        return ResponseEntity.ok(authService.login(regRequest, request));
    }
}
