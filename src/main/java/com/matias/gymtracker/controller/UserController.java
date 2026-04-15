package com.matias.gymtracker.controller;

import com.matias.gymtracker.dto.request.UserCreateRequest;
import com.matias.gymtracker.dto.request.UserLoginRequest;
import com.matias.gymtracker.dto.response.UserResponse;
import com.matias.gymtracker.entity.User;
import com.matias.gymtracker.mapper.UserMapper;
import com.matias.gymtracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody UserCreateRequest request) {
        User created = userService.registerUser(userMapper.toEntity(request));
        return userMapper.toResponse(created);
    }

    @PostMapping("/login")
    public UserResponse login(@Valid @RequestBody UserLoginRequest request) {
        User user = userService.loginUser(request.getEmail(), request.getPassword());
        return userMapper.toResponse(user);
    }
}
