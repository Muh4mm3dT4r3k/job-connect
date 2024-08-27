package com.mohamed.jobconnectv2.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

public record UpdateUserRequest(
        @NotNull
        UUID userId,
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
)implements Serializable {
}
