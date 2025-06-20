package com.maxiamikel.userAuthApi.service.auth;

import com.maxiamikel.userAuthApi.dto.AccessTokenDto;
import com.maxiamikel.userAuthApi.dto.LoginRequestDto;
import com.maxiamikel.userAuthApi.dto.UserRequestDto;
import com.maxiamikel.userAuthApi.entity.User;

public interface AuthService {
    User register(UserRequestDto request);

    AccessTokenDto login(LoginRequestDto credentials);
}
