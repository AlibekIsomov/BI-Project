package com.bim.inventory.security;


import com.bim.inventory.entity.Role;
import com.bim.inventory.entity.User;
import com.bim.inventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class AdminUserInitializer implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Optional<User> uk = userRepository.findByUsername("admin123");
        if (!uk.isPresent()) {
            User u = new User();
            u.setName("admin");
            u.setSurname("Admin");
            u.setUsername("bonnaz2023");
            u.setPassword(passwordEncoder.encode("bim2021"));
            u.setActive(true);
            u.setRoles(Set.of(Role.ADMIN, Role.MANAGER, Role.USER));
            userRepository.save(u);
        }
    }
}
