package com.mohamed.jobconnectv2.message;

import com.mohamed.jobconnectv2.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    private UUID id;
    private String messageText;
    @ManyToOne
    @JoinColumn(name = "fk_sender_id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "fk_receiver_id)")
    private User receiver;
    private LocalDateTime sentAt;

    @Builder
    public Message (UUID id, String messageText, LocalDateTime sentAt) {
        this.id = id;
        this.messageText = messageText;
        this.sentAt = sentAt;
    }
}
