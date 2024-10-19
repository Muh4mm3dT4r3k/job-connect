package com.mohamed.jobconnectv2.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    @Query("""
    SELECT m FROM Message m WHERE (m.sender.id = :userId1 AND  m.receiver.id = :userId2 ) OR  (m.sender.id = :userId2 AND  m.receiver.id = :userId1 )
    """)
    Page<Message> findMessagesBetweenTwoUsers(UUID userId1, UUID userId2, Pageable pageable);
}