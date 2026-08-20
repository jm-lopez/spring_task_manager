package com.juanmatiaslopez.taskmanager.DTO;

import com.juanmatiaslopez.taskmanager.Enum.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegLoginRequest {

    @Email(message = "Please provide a valid Email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private Role role;
}
