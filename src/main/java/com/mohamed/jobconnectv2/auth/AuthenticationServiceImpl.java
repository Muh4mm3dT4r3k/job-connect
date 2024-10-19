package com.mohamed.jobconnectv2.auth;

import com.mohamed.jobconnectv2.auth.dto.UserLoginRequest;
import com.mohamed.jobconnectv2.auth.dto.UserLoginResponse;
import com.mohamed.jobconnectv2.auth.dto.UserRegisterRequest;
import com.mohamed.jobconnectv2.auth.dto.UserRegisterResponse;
import com.mohamed.jobconnectv2.role.Role;
import com.mohamed.jobconnectv2.role.RoleRepository;
import com.mohamed.jobconnectv2.security.jwt.JwtService;
import com.mohamed.jobconnectv2.user.User;
import com.mohamed.jobconnectv2.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public UserRegisterResponse register(UserRegisterRequest request) {
        checkIfUsernameOrEmailAvailable(request.username(), request.email());
        User user = saveUserInDB(request);
        String token = jwtService.generateToken(user);
        return new UserRegisterResponse(token);
    }


    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        var user = (User) authenticate.getPrincipal();
        String token = jwtService.generateToken(user);
        return new UserLoginResponse(token);
    }

    private User saveUserInDB(UserRegisterRequest request) {
        Role role = findRoleByName("JOB_SEEKER");
        User user = User
                .builder()
                .id(UUID.randomUUID())
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .isEnabled(true)
                .isNonLooked(true)
                .createdAt(LocalDateTime.now())
                .build();
        return userRepository.save(user);
    }

    private Role findRoleByName(String jobSeeker) {
        return roleRepository
                .findByName(jobSeeker)
                .orElseThrow(()-> new RuntimeException("role not found"));
        // TODO handle app exception
    }


    private void checkIfUsernameOrEmailAvailable(String username, String email) {
        boolean emailExists = userRepository.existsUserByEmail(email);
        boolean usernameExists = userRepository.existsUserByUsername(username);

        if (emailExists)
            throw new RuntimeException("email is already exists"); // TODO handle app exception

        if (usernameExists)
            throw new RuntimeException("email is already exists"); // TODO handle app exception
    }
}
