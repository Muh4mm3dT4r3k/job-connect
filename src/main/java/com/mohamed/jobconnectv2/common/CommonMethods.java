package com.mohamed.jobconnectv2.common;

import com.mohamed.jobconnectv2.role.Role;
import com.mohamed.jobconnectv2.role.RoleRepository;
import com.mohamed.jobconnectv2.user.User;
import com.mohamed.jobconnectv2.user.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CommonMethods {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    
    public  User findUserByIdOrThrow(@NotNull UUID userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));
        // TODO handle exception
    }

    public  Role findRoleByName(String role) {
        return roleRepository
                .findByName(role)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        // TODO handle exception
    }

    public  void checkUsernameAlreadyExist(String username) {
        boolean existsUserByUsername = userRepository.existsUserByUsername(username);
        if (existsUserByUsername)
            throw new RuntimeException("username already exists");
        //TODO handle exception
    }

    public  void checkEmailAlreadyExist(String email) {
        boolean existsUserByEmail = userRepository.existsUserByEmail(email);
        if (existsUserByEmail)
            throw new RuntimeException("email already exists");
        // TODO handle exception
    }
}
