package com.mohamed.jobconnectv2.message.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.mohamed.jobconnectv2.message.Message}
 */
public record SendMessageRequest(
        @NotBlank(message = "message is required")
        String messageText,
        @NotNull(message = "user to id is required")
        UUID userTo
) implements Serializable {
}