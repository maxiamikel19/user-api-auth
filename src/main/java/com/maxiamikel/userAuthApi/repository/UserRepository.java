package com.maxiamikel.userAuthApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maxiamikel.userAuthApi.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
