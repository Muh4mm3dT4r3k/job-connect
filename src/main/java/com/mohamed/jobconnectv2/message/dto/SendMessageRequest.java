package com.mohamed.jobconnectv2.message.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.mohamed.jobconnectv2.message.Message}
 */
public record SendMessageRequest(
        @NotBlank(message = "message is required")
        String messageText,
        @NotBlank(message = "receive id is required")
        UUID receiverId
) implements Serializable {
}