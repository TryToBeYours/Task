package org.example.configuration;

import org.example.model.Role;
import org.example.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RoleSeeder {

    @Bean
    CommandLineRunner preloadRoles(RoleRepository roleRepository) {
        return args -> {
            List<String> roles = List.of(
                    "ROLE_ADMIN",
                    "ROLE_AGENT",
                    "ROLE_CUSTOMER"
            );

            for (String roleName : roles) {
                roleRepository.findByName(roleName)
                        .orElseGet(() -> roleRepository.save(new Role(null, roleName)));
            }
        };
    }
}
