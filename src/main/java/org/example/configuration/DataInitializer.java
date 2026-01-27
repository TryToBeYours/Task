package org.example.configuration;

import org.example.model.Role;
import org.example.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("ADMIN").isEmpty()) {
                roleRepository.save(new Role(null, "ADMIN"));
            }

            if (roleRepository.findByName("AGENT").isEmpty()) {
                roleRepository.save(new Role(null, "AGENT"));
            }

            if (roleRepository.findByName("CUSTOMER").isEmpty()) {
                roleRepository.save(new Role(null, "CUSTOMER"));
            }
        };
    }
}
