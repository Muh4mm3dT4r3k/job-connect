package com.mohamed.jobconnectv2.message;

import com.mohamed.jobconnectv2.common.CommonMethods;
import com.mohamed.jobconnectv2.message.dto.MessageResponse;
import com.mohamed.jobconnectv2.message.dto.SendMessageRequest;
import com.mohamed.jobconnectv2.security.SecurityUtils;
import com.mohamed.jobconnectv2.user.User;
import com.mohamed.jobconnectv2.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Transactional
    public void send(SendMessageRequest request) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        User sender = CommonMethods.findUserByIdOrThrow(currentUserId, userRepository);
        User receiver = CommonMethods.findUserByIdOrThrow(request.userTo(), userRepository);
        Message message = Message
                .builder()
                .id(UUID.randomUUID())
                .messageText(request.messageText())
                .sentAt(LocalDateTime.now())
                .build();
        message.setReceiver(receiver);
        message.setSender(sender);
        messageRepository.save(message);
    }

    public Page<MessageResponse> getMessages(UUID userId, Pageable pageable) {
        return messageRepository
                .findMessagesBetweenTwoUsers(SecurityUtils.getCurrentUserId(), userId, pageable);
    }
}
