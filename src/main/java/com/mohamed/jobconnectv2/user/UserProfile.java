package com.mohamed.jobconnectv2.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class UserProfile {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate dayOfBirth;

}
