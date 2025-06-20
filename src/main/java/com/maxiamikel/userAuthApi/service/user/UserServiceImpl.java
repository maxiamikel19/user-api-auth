package com.maxiamikel.userAuthApi.service.user;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.maxiamikel.userAuthApi.dto.PasswordResetRequestDto;
import com.maxiamikel.userAuthApi.dto.RoleChangeRequestDto;
import com.maxiamikel.userAuthApi.entity.Role;
import com.maxiamikel.userAuthApi.entity.User;
import com.maxiamikel.userAuthApi.exception.ResourceNotFoundException;
import com.maxiamikel.userAuthApi.exception.UnauthorizedException;
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
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUserById(Long id) {
        return findById(id);
    }

    @Override
    public void deleteUserRegister(Long id, String authenticatedUser) {
        User userToDelete = findById(id);
        User loggedUser = getAuthenticatedUser(authenticatedUser);
        if (!userToDelete.getId().equals(loggedUser.getId()) && !isUserAdmin(loggedUser)) {
            throw new UnauthorizedException("User don´t have any privileges to make this action");
        }
        userRepository.deleteById(userToDelete.getId());

    }

    @Override
    public void updateUserPassword(Long id, PasswordResetRequestDto requestDto, String authenticatedUser) {
        User user = getAuthenticatedUser(authenticatedUser);

        if (!verifyPasswordMatches(requestDto.getOldPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid user credentials");
        }

        if (!requestDto.getNewPassword().equals(requestDto.getConfirmationPassword())) {
            throw new UnauthorizedException("Password confirmation not matches");
        }

        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        userRepository.save(user);

    }

    @Override
    public void changeUserRole(Long id, RoleChangeRequestDto requestDto, String authenticatedUser) {

        User loggedUser = getAuthenticatedUser(authenticatedUser);

        if (!isUserAdmin(loggedUser)) {
            throw new UnauthorizedException("User has no permission to perform this action");
        }

        User user = findById(id);

        Role adminRole = roleRepository.findByName(requestDto.getRole().toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Role " + requestDto.getRole() + " not found"));

        Set<Role> updatedRoles = new HashSet<>(user.getRoles());
        updatedRoles.add(adminRole);
        user.setRoles(updatedRoles);

        userRepository.save(user);
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id:" + id + " not found"));
    }

    private User getAuthenticatedUser(String username) {
        User authUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not logged!"));
        return authUser;
    }

    private boolean verifyPasswordMatches(String rawPassword, String userPassword) {
        return passwordEncoder.matches(rawPassword, userPassword);
    }

    private boolean isUserAdmin(User user) {
        return user.getRoles()
                .stream()
                .anyMatch(role -> "ADMIN".equalsIgnoreCase(role.getName()));
    }

}
