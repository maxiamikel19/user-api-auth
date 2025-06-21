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
import com.maxiamikel.userAuthApi.entity.User;
import com.maxiamikel.userAuthApi.mapper.UserMapper;
import com.maxiamikel.userAuthApi.service.auth.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(description = "Endpoints to authenticate users", name = "Authentication Controller")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Create user account", description = "Create a new user account with username and password", responses = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "200", description = "User accound successfully created"),
            @ApiResponse(responseCode = "422", description = "Input data is required"),
            @ApiResponse(responseCode = "409", description = "Username already taken")
    })
    public ResponseEntity<?> register(@RequestBody @Valid UserRequestDto request) {
        var user = authService.register(request);
        var userDto = UserMapper.mapToDto(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PostMapping("/login")
    @Operation(summary = "Account login", description = "Access your account with username and password", responses = {
            @ApiResponse(responseCode = "200", description = "Return the access token", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Username or password incorrect!")
    })
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto credentials) {
        var token = authService.login(credentials);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
