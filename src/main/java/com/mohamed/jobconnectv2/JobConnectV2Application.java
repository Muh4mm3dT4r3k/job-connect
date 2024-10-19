package com.mohamed.jobconnectv2;

import com.mohamed.jobconnectv2.role.Role;
import com.mohamed.jobconnectv2.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
public class JobConnectV2Application {

    public static void main(String[] args) {
        SpringApplication.run(JobConnectV2Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
        return args -> {
            Role role = new Role();
            role.setId(UUID.randomUUID());
            role.setName("JOB_SEEKER");
            role.setCreatedAt(LocalDateTime.now());
            roleRepository.save(role);
        };
    }

}
