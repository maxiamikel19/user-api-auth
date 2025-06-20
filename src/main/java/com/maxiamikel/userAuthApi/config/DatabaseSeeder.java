package com.maxiamikel.userAuthApi.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.maxiamikel.userAuthApi.entity.Role;
import com.maxiamikel.userAuthApi.repository.RoleRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.count() == 0) {
            Role admin = new Role();
            admin.setId(null);
            admin.setName("ADMIN");

            Role guest = new Role();
            guest.setId(null);
            guest.setName("GUEST");

            roleRepository.saveAll(List.of(admin, guest));
        }
    }

}
