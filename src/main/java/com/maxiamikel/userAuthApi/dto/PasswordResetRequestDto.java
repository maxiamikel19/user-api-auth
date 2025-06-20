package com.maxiamikel.userAuthApi.dto;

import jakarta.validation.constraints.NotBlank;

public class PasswordResetRequestDto {

    @NotBlank(message = "New password is required!")
    private String newPassword;
    @NotBlank(message = "Youy old password is required!")
    private String oldPassword;
}
