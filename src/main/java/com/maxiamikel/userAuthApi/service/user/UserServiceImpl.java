package com.maxiamikel.userAuthApi.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.maxiamikel.userAuthApi.dto.PasswordResetRequestDto;
import com.maxiamikel.userAuthApi.dto.RoleChangeRequestDto;
import com.maxiamikel.userAuthApi.entity.User;
import com.maxiamikel.userAuthApi.repository.RoleRepository;
import com.maxiamikel.userAuthApi.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public void deleteUserRegister(Long id, String authenticatedUser) {

    }

    @Override
    public void updateUserPassword(Long id, PasswordResetRequestDto requestDto, String authenticatedUser) {

    }

    @Override
    public void updateUserRole(Long id, RoleChangeRequestDto request, String authenticatedUser) {

    }

}
