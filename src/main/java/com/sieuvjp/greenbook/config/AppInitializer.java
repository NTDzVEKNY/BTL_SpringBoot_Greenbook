package com.sieuvjp.greenbook.config;

import com.sieuvjp.greenbook.entity.User;
import com.sieuvjp.greenbook.enums.Role;
import com.sieuvjp.greenbook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create admin user if no users exist
        if (userRepository.count() == 0) {
            log.info("No users found, creating default admin user");
            createAdminUser();
        }
    }

    private void createAdminUser() {
        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .email("admin@greenbook.com")
                .fullName("System Administrator")
                .role(Role.ADMIN)
                .isActive(true)
                .build();

        userRepository.save(admin);
        log.info("Default admin user created successfully");
        log.info("Username: admin");
        log.info("Password: admin123");
        log.info("Please change this password after first login!");
    }
}