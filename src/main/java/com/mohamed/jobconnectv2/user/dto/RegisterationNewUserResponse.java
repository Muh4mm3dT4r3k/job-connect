package com.mohamed.jobconnectv2.user.dto;

import com.mohamed.jobconnectv2.user.User;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record RegisterationNewUserResponse(
        UUID id,
        String username,
        String email,
        boolean isEnabled,
        boolean isNonLooked,
        String role,
        LocalDateTime createdAt
) implements Serializable {
    public static RegisterationNewUserResponse from(User user) {
        return RegisterationNewUserResponse
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
