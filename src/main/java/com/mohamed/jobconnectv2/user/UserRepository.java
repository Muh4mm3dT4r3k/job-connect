package com.mohamed.jobconnectv2.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("""
    SELECT u FROM User u WHERE u.email = :email
    """)
    Optional<User> findByEmail(String email);

    @Query("""
    SELECT u FROM User u WHERE u.username = :username
    """)
    Optional<User> findByUsername(String username);
}