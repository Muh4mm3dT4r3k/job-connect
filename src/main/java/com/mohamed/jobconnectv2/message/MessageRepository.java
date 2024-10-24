package com.mohamed.jobconnectv2.message;

import com.mohamed.jobconnectv2.message.dto.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    @Query("""
        SELECT new com.mohamed.jobconnectv2.message.dto.MessageResponse(m.id, m.messageText, m.sender.id, m.receiver.id, m.sentAt) 
        FROM Message m 
        WHERE (m.sender.id = :userId1 AND  m.receiver.id = :userId2 ) OR  (m.sender.id = :userId2 AND  m.receiver.id = :userId1 )
        """)
    Page<MessageResponse> findMessagesBetweenTwoUsers(UUID userId1, UUID userId2, Pageable pageable);
}