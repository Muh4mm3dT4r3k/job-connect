package com.mohamed.jobconnectv2.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

public record CreateUserProfileRequest(
        @NotBlank(message = "first name is required")
        String firstName,
        @NotBlank(message = "last name is required")
        String lastName,
        @NotNull(message = "date of birth is required")
        String email,
        LocalDate dayOfBirth,
        String cv,
        String photo
) implements Serializable {
}
