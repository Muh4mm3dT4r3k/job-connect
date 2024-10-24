package com.mohamed.jobconnectv2.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private LocalDate dayOfBirth;
    private String cv;
    private String photo;
    @OneToOne
    private User user;
}
