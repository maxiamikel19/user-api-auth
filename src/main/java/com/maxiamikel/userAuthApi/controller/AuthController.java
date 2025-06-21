package com.maxiamikel.userAuthApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxiamikel.userAuthApi.dto.LoginRequestDto;
import com.maxiamikel.userAuthApi.dto.UserRequestDto;
import com.maxiamikel.userAuthApi.mapper.UserMapper;
import com.maxiamikel.userAuthApi.service.auth.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequestDto request) {
        var user = authService.register(request);
        var userDto = UserMapper.mapToDto(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto credentials) {
        var token = authService.login(credentials);
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }
}
