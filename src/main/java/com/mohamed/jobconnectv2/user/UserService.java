package com.mohamed.jobconnectv2.user;

import com.mohamed.jobconnectv2.common.CommonMethods;
import com.mohamed.jobconnectv2.role.Role;
import com.mohamed.jobconnectv2.role.RoleRepository;
import com.mohamed.jobconnectv2.user.dto.RegisterNewUserRequest;
import com.mohamed.jobconnectv2.user.dto.RegisterationNewUserResponse;
import com.mohamed.jobconnectv2.user.dto.UpdateUserRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public RegisterationNewUserResponse registerUserByAdmin(RegisterNewUserRequest request) {
        Role role = CommonMethods.findRoleByName(request.role(), roleRepository);
        CommonMethods.checkEmailAlreadyExist(request.email(), userRepository);
        CommonMethods.checkUsernameAlreadyExist(request.username(), userRepository);
        User user = RegisterNewUserRequest.from(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(role);
        userRepository.save(user);
        return RegisterationNewUserResponse.from(user);
    }



    @Transactional
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(UpdateUserRequest request) {
        Role role = CommonMethods.findRoleByName(request.role(), roleRepository);
        User user = CommonMethods.findUserByIdOrThrow(request.userId(), userRepository);
        user.setRole(role);
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEnabled(request.isEnabled());
        user.setNonLooked(request.isNonLooked());
        userRepository.save(user);
    }

    public User getUserById(UUID id) {
        return CommonMethods.findUserByIdOrThrow(id, userRepository);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
