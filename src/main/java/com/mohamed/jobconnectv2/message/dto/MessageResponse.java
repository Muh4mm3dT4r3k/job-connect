package com.mohamed.jobconnectv2.message.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record MessageResponse(
        UUID messageId,
        String messageText,
        UUID senderId,
        UUID receiverId,
        LocalDateTime sentAt
) implements Serializable {
}
