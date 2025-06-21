package com.maxiamikel.userAuthApi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDto {
    @NotBlank(message = "Username is required!")
    @Schema(description = "Username*, ex: myusername")
    private String username;
    @NotBlank(message = "Password is required!")
    @Schema(description = "Password *, ex: oser213")
    private String password;
}
