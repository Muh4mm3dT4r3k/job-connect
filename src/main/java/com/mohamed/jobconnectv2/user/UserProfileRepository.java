package com.mohamed.jobconnectv2.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {

    @Query("""
    SELECT up FROM UserProfile up WHERE up.user.id = :userId
    """)
    Optional<UserProfile> findUserProfileByUserId(UUID userId);
}