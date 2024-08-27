package com.mohamed.jobconnectv2.user;

import com.mohamed.jobconnectv2.role.Role;
import com.mohamed.jobconnectv2.role.RoleRepository;
import com.mohamed.jobconnectv2.user.dto.RegisterNewUserRequest;
import com.mohamed.jobconnectv2.user.dto.RegisterNewUserResponse;
import com.mohamed.jobconnectv2.user.dto.UpdateUserRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        checkEmailAlreadyExist(request.email());
        checkUsernameAlreadyExist(request.username());
        User user = RegisterNewUserRequest.from(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(role);
        userRepository.save(user);
        return RegisterNewUserResponse.from(user);
    }



    @Transactional
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(UpdateUserRequest request) {
        Role role = findRoleByName(request.role());
        User user = findUserByIdOrThrow(request.userId());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setEnabled(request.isEnabled());
        user.setNonLooked(request.isNonLooked());
        userRepository.save(user);
    }

    public User getUserById(UUID id) {
        return findUserByIdOrThrow(id);
    }

    private User findUserByIdOrThrow(@NotNull UUID userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));
        // TODO handle exception
    }

    private Role findRoleByName(String role) {
        return roleRepository
                .findByName(role)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        // TODO handle exception
    }

    private void checkUsernameAlreadyExist(String username) {
        boolean existsUserByUsername = userRepository.existsUserByUsername(username);
        if (existsUserByUsername)
            throw new RuntimeException("username already exists");
        //TODO handle exception
    }

    private void checkEmailAlreadyExist(String email) {
        boolean existsUserByEmail = userRepository.existsUserByEmail(email);
        if (existsUserByEmail)
            throw new RuntimeException("email already exists");
        // TODO handle exception
    }


}
