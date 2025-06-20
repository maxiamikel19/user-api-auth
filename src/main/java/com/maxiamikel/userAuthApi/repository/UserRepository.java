package com.maxiamikel.userAuthApi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maxiamikel.userAuthApi.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
