package com.maxiamikel.userAuthApi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccessTokenDto {
    private String accessToken;
    private Long expiration;
}
