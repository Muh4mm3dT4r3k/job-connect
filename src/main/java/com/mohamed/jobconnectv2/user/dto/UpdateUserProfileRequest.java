package com.mohamed.jobconnectv2.user.dto;

import com.mohamed.jobconnectv2.user.UserProfile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link UserProfile}
 */
public record UpdateUserProfileRequest(
        String firstName,
        String lastName,
        LocalDate dayOfBirth,
        MultipartFile cv,
        MultipartFile photo
) implements Serializable {
}