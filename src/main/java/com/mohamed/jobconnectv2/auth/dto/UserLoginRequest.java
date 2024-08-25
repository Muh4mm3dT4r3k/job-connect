package com.mohamed.jobconnectv2.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.mohamed.jobconnectv2.user.User}
 */

public record UserLoginRequest(
        @NotBlank(message = "username is required")
        String username,
        @NotBlank(message = "password is required")
        String password
) implements Serializable {
}