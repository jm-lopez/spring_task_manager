package com.juanmatiaslopez.taskmanager.Service.impl;

import com.juanmatiaslopez.taskmanager.DTO.ApiResponse;
import com.juanmatiaslopez.taskmanager.DTO.RegLoginRequest;
import com.juanmatiaslopez.taskmanager.DTO.UserDTO;
import com.juanmatiaslopez.taskmanager.Enum.Role;
import com.juanmatiaslopez.taskmanager.Exception.BadRequestException;
import com.juanmatiaslopez.taskmanager.Model.User;
import com.juanmatiaslopez.taskmanager.Repository.UserRepository;
import com.juanmatiaslopez.taskmanager.Service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ApiResponse<?> register(RegLoginRequest regLoginRequest) {

        if (userRepository.findUserByEmail(regLoginRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Email already registered.");
        }
        User user = new User();
        user.setEmail(regLoginRequest.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(regLoginRequest.getPassword()));

        if (regLoginRequest.getRole() != null && regLoginRequest.getRole().equals(Role.ADMIN)) user.setRole(Role.ADMIN);
        else user.setRole(Role.USER);

        User savedUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());

        return new ApiResponse<>(201, "User created succesfully.", userDTO);
    }

    @Override
    public ApiResponse<?> login(RegLoginRequest regLoginRequest, HttpServletRequest request) {

        //Validates the data using customuserdetailservice and customuserdetails
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        regLoginRequest.getEmail(),
                        regLoginRequest.getPassword())
        );

        //Saves the context to security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //Creates a cookie session with JSESSIONID for the user
        HttpSession session = request.getSession(true);

        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        return new ApiResponse<>(200, "Login successfully", null);
    }
}
