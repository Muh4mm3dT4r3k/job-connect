package com.mohamed.jobconnectv2.user;

import com.mohamed.jobconnectv2.common.CommonMethods;
import com.mohamed.jobconnectv2.role.Role;
import com.mohamed.jobconnectv2.user.dto.RegisterNewUserRequest;
import com.mohamed.jobconnectv2.user.dto.RegisterNewUserResponse;
import com.mohamed.jobconnectv2.user.dto.UpdateUserRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final CommonMethods commonMethods;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Transactional
    public RegisterNewUserResponse userRegister(RegisterNewUserRequest request) {
        Role role = commonMethods.findRoleByName(request.role());
        commonMethods.checkEmailAlreadyExist(request.email());
        commonMethods.checkUsernameAlreadyExist(request.username());
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
        Role role = commonMethods.findRoleByName(request.role());
        User user = commonMethods.findUserByIdOrThrow(request.userId());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setEnabled(request.isEnabled());
        user.setNonLooked(request.isNonLooked());
        userRepository.save(user);
    }

    public User getUserById(UUID id) {
        return commonMethods.findUserByIdOrThrow(id);
    }

}
