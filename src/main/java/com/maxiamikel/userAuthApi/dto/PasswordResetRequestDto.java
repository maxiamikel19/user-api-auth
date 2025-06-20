package com.maxiamikel.userAuthApi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordResetRequestDto {

    @NotBlank(message = "Youy old password is required!")
    private String oldPassword;
    @NotBlank(message = "New password is required!")
    private String newPassword;
    @NotBlank(message = "You have to confirm your password!")
    private String confirmationPassword;
}
