package com.maxiamikel.userAuthApi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maxiamikel.userAuthApi.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String string);

}
