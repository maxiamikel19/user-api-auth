package com.maxiamikel.userAuthApi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maxiamikel.userAuthApi.dto.PasswordResetRequestDto;
import com.maxiamikel.userAuthApi.dto.RoleChangeRequestDto;
import com.maxiamikel.userAuthApi.mapper.UserMapper;
import com.maxiamikel.userAuthApi.service.user.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    Map<String, String> message = new HashMap<>();

    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        var pageUsers = userService.getAllUsers(pageable);
        var pageDtos = pageUsers.stream().map(user -> UserMapper.mapToDto(user));
        return ResponseEntity.status(HttpStatus.OK).body(pageDtos);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        var user = userService.getUserById(id);
        var userDto = UserMapper.mapToDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserRegister(@PathVariable Long id, JwtAuthenticationToken authToken) {
        String username = authToken.getToken().getClaim("username");
        userService.deleteUserRegister(id, username);
        message.put("Success", "Successfully deleted");
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PutMapping("/user/{id}/reset-password")
    public ResponseEntity<?> updateUserPassword(@PathVariable Long id,
            @RequestBody @Valid PasswordResetRequestDto requestDto, JwtAuthenticationToken authToken) {
        String username = authToken.getToken().getClaim("username");
        userService.updateUserPassword(id, requestDto, username);
        message.put("Success", "Your password was successfully changed");
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PutMapping("/user/{id}/upgrade")
    public ResponseEntity<?> changeUserRole(@PathVariable Long id, @RequestBody @Valid RoleChangeRequestDto requestDto,
            JwtAuthenticationToken authToken) {
        String username = authToken.getToken().getClaim("username");
        userService.changeUserRole(id, requestDto, username);
        message.put("Success", "User role was successfully upgraded");
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
