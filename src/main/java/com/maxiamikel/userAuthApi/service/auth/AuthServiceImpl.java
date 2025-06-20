package com.maxiamikel.userAuthApi.service.auth;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.maxiamikel.userAuthApi.dto.AccessTokenDto;
import com.maxiamikel.userAuthApi.dto.LoginRequestDto;
import com.maxiamikel.userAuthApi.dto.UserRequestDto;
import com.maxiamikel.userAuthApi.entity.Role;
import com.maxiamikel.userAuthApi.entity.User;
import com.maxiamikel.userAuthApi.repository.RoleRepository;
import com.maxiamikel.userAuthApi.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(UserRequestDto request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            log.warn("Username already esists in the users table : {}", request.getUsername());
            throw new RuntimeException("Username already taken");
        }

        User user = new User();

        user.setId(null);
        user.setPassword(passwordEncoder.encode(request.getPassword().trim()));
        user.setUsername(request.getUsername().trim());
        assignDefaultRoleToUser(user);

        var newUser = userRepository.save(user);
        log.info("New user: {} successfully created", newUser.getUsername());

        return newUser;
    }

    @Override
    public AccessTokenDto login(LoginRequestDto credentials) {
        return null;
    }

    private void assignDefaultRoleToUser(User user) {
        Role guest = roleRepository.findByName("GUEST")
                .orElseThrow(() -> new EntityNotFoundException("Role GUEST not found"));

        if (userRepository.count() == 0) {
            Role admin = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new EntityNotFoundException("Role ADMIN not found"));
            user.setRoles(Set.of(admin, guest));
        }
        user.setRoles(Set.of(guest));
    }

}
