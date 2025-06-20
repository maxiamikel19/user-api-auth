package com.maxiamikel.userAuthApi.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private Set<String> roles;
}
