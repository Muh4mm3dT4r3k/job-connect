package com.mohamed.jobconnectv2.user.dto;

import com.mohamed.jobconnectv2.user.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record RegisterNewUserRequest(
        @NotBlank(message = "username is required")
        String username,
        @NotBlank(message = "email is required")
        String email,
        @NotBlank(message = "password is required")
        String password,
        boolean isEnabled,
        boolean isNonLooked,
        @NotBlank(message = "role is required")
        String role
) implements Serializable {
    public static User from(RegisterNewUserRequest request) {
        return User
                .builder()
                .id(UUID.randomUUID())
                .username(request.username())
                .email(request.email())
                .isNonLooked(request.isNonLooked())
                .isEnabled(request.isEnabled())
                .createdAt(LocalDateTime.now())
                .build();
    }
}