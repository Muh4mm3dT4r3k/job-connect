package com.mohamed.jobconnectv2.user.dto;

import com.mohamed.jobconnectv2.user.User;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record RegisterNewUserResponse (
        UUID id,
        String username,
        String email,
        boolean isEnabled,
        boolean isNonLooked,
        String role,
        LocalDateTime createdAt
) implements Serializable {
    public static RegisterNewUserResponse from(User user) {
        return RegisterNewUserResponse
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().getAuthority())
                .isEnabled(user.isEnabled())
                .isNonLooked(user.isNonLooked())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
