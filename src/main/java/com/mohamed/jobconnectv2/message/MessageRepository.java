package com.mohamed.jobconnectv2.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    Page<Message> findBySenderIdAndReceiverId(UUID senderId, UUID receiverId, Pageable pageable);
}