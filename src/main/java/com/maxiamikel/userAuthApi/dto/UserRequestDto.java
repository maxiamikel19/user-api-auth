package com.maxiamikel.userAuthApi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank(message = "Username is required!")
    @Pattern(regexp = "^[^\\s]+$", message = "Username must not contain spaces")
    private String username;
    @NotBlank(message = "Password is required!")
    private String password;
}
