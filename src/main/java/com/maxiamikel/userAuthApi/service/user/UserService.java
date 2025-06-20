package com.maxiamikel.userAuthApi.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maxiamikel.userAuthApi.dto.PasswordResetRequestDto;
import com.maxiamikel.userAuthApi.dto.RoleChangeRequestDto;
import com.maxiamikel.userAuthApi.entity.User;

public interface UserService {

    Page<User> getAllUsers(Pageable pageable);

    User getUserById(Long id);

    void deleteUserRegister(Long id, String authenticatedUser);

    void updateUserPassword(Long id, PasswordResetRequestDto requestDto, String authenticatedUser);

    void changeUserRole(Long id, RoleChangeRequestDto requestDto, String authenticatedUser);
}
