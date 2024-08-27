package com.mohamed.jobconnectv2.user;

import com.mohamed.jobconnectv2.user.dto.RegisterNewUserRequest;
import com.mohamed.jobconnectv2.user.dto.RegisterNewUserResponse;
import com.mohamed.jobconnectv2.user.dto.UpdateUserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @DeleteMapping("/admin/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/admin/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> updateUser(UpdateUserRequest request) {
        userService.updateUser(request);
        return ResponseEntity.ok("User updated successfully");
    }
}
