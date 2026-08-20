package com.juanmatiaslopez.taskmanager.Controller;

import com.juanmatiaslopez.taskmanager.DTO.ApiResponse;
import com.juanmatiaslopez.taskmanager.DTO.UserDTO;
import com.juanmatiaslopez.taskmanager.Model.User;
import com.juanmatiaslopez.taskmanager.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/profile/me")
    public ResponseEntity<ApiResponse<UserDTO>> getProfile(Authentication auth) {
        return ResponseEntity.ok(userService.getProfile(auth.getName()));
    }

    @PutMapping("/profile/update")
    public ResponseEntity<ApiResponse<UserDTO>> updateProfile(@RequestBody UserDTO user, Authentication auth) {
        return ResponseEntity.ok(userService.updateProfile(auth.getName(), user));
    }
}
