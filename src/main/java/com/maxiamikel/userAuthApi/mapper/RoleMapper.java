package com.maxiamikel.userAuthApi.mapper;

import com.maxiamikel.userAuthApi.dto.RoleDto;
import com.maxiamikel.userAuthApi.entity.Role;

public class RoleMapper {

    public static RoleDto mapToDto(Role entity) {
        return RoleDto
                .builder()
                .name(entity.getName())
                .build();
    }

}
