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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User controller", description = "Contains the endpoints to manage the users account")
public class UserController {

    @Autowired
    private UserService userService;

    Map<String, String> message = new HashMap<>();

    @GetMapping
    @Operation(summary = "Get All Users Paginate", description = "Return pages of all users", responses = {
            @ApiResponse(responseCode = "200", description = "Page of users"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    }, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        var pageUsers = userService.getAllUsers(pageable);
        var pageDtos = pageUsers.stream().map(user -> UserMapper.mapToDto(user));
        return ResponseEntity.status(HttpStatus.OK).body(pageDtos);
    }

    @GetMapping("/find/{id}")
    @Operation(description = "Get a specific user by id", summary = "Get By Id", security = @SecurityRequirement(name = "bearerAuth"), responses = {
            @ApiResponse(responseCode = "200", description = "Return the found user"),
            @ApiResponse(responseCode = "400", description = "Invalid ID format"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User id not found")
    })

    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        var user = userService.getUserById(id);
        var userDto = UserMapper.mapToDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @Operation(description = "Delete a user", summary = "Delete user", security = @SecurityRequirement(name = "bearerAuth"), responses = {
            @ApiResponse(responseCode = "200", description = "User was successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid ID format / URL not available"),
            @ApiResponse(responseCode = "401", description = "User don´t have any privileges to make this action"),
            @ApiResponse(responseCode = "404", description = "User id not found")
    })
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteUserRegister(@PathVariable Long id, JwtAuthenticationToken authToken) {
        String username = authToken.getToken().getClaim("username");
        userService.deleteUserRegister(id, username);
        message.put("Success", "Successfully deleted");
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PutMapping("/user/{id}/reset-password")
    @Operation(description = "Change password", summary = "Update a user password", security = @SecurityRequirement(name = "bearerAuth"), responses = {
            @ApiResponse(responseCode = "200", description = "Your password was successfully changed"),
            @ApiResponse(responseCode = "400", description = "Password confirmation not matches"),
            @ApiResponse(responseCode = "401", description = "User don´t have any privileges to make this action"),
            @ApiResponse(responseCode = "404", description = "User id not found"),
            @ApiResponse(responseCode = "502", description = "URL not available")
    })
    public ResponseEntity<?> updateUserPassword(@PathVariable Long id,
            @RequestBody @Valid PasswordResetRequestDto requestDto, JwtAuthenticationToken authToken) {
        String username = authToken.getToken().getClaim("username");
        userService.updateUserPassword(id, requestDto, username);
        message.put("Success", "Your password was successfully changed");
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PutMapping("/user/{id}/upgrade")
    @Operation(description = "Change user role", summary = "Update a user role only by ADMIN ", security = @SecurityRequirement(name = "bearerAuth"), responses = {
            @ApiResponse(responseCode = "200", description = "Your password was successfully changedUser role was successfully upgraded"),
            @ApiResponse(responseCode = "401", description = "User don´t have any privileges to make this action"),
            @ApiResponse(responseCode = "404", description = "User id not found / role not found"),
    })
    public ResponseEntity<?> changeUserRole(@PathVariable Long id, @RequestBody @Valid RoleChangeRequestDto requestDto,
            JwtAuthenticationToken authToken) {
        String username = authToken.getToken().getClaim("username");
        userService.changeUserRole(id, requestDto, username);
        message.put("Success", "User role was successfully upgraded");
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
