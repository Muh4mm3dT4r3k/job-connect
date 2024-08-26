package com.mohamed.jobconnectv2.user;

import com.mohamed.jobconnectv2.user.dto.RegisterNewUserRequest;
import com.mohamed.jobconnectv2.user.dto.RegisterNewUserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/admin/register")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RegisterNewUserResponse registerNewUser(@RequestBody @Valid RegisterNewUserRequest request) {
        return userService.userRegister(request);
    }
}
