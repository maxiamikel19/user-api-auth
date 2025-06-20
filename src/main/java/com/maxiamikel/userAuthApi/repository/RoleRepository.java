package com.maxiamikel.userAuthApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maxiamikel.userAuthApi.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
