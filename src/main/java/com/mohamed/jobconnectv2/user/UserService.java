package com.mohamed.jobconnectv2.user;

import com.mohamed.jobconnectv2.role.Role;
import com.mohamed.jobconnectv2.role.RoleRepository;
import com.mohamed.jobconnectv2.user.dto.RegisterNewUserRequest;
import com.mohamed.jobconnectv2.user.dto.RegisterNewUserResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Transactional
    public RegisterNewUserResponse userRegister(RegisterNewUserRequest request) {
        Role role = findRoleByName(request.role());
        User user = RegisterNewUserRequest.from(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(role);
        userRepository.save(user);
        return RegisterNewUserResponse.from(user);
    }

    private Role findRoleByName(String role) {
        return roleRepository
                .findByName(role)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        // TODO handle exception
    }
}
