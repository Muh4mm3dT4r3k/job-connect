package com.mohamed.jobconnectv2.user.dto;

import com.mohamed.jobconnectv2.user.UserProfile;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


public record EmployerDto(
        UUID id,
        String username,
        String email,
        LocalDateTime createdAt,
        UserProfile userProfile
) implements Serializable {
}