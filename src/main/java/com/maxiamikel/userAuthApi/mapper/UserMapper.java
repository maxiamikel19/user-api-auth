package com.maxiamikel.userAuthApi.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.maxiamikel.userAuthApi.dto.UserDto;
import com.maxiamikel.userAuthApi.entity.Role;
import com.maxiamikel.userAuthApi.entity.User;

public class UserMapper {

    public static UserDto mapToDto(User entity) {
        var roles = entity.getRoles().stream().map(Role::getName).collect(Collectors.joining(" "));
        return UserDto
                .builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .roles(Set.of(roles))
                .build();
    }

}
