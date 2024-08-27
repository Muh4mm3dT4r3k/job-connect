package com.mohamed.jobconnectv2.user;

import com.mohamed.jobconnectv2.user.dto.EmployerDto;
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

    @Query("""
    SELECT COUNT (u) > 0 FROM User u WHERE u.email = :email
    """)
    boolean existsUserByEmail(String email);

    @Query("""
    SELECT COUNT (u) > 0 FROM User u WHERE u.username = :username
    """)
    boolean existsUserByUsername(String username);

    @Query("""
    SELECT new com.mohamed.jobconnectv2.user.dto.EmployerDto(
     u.id,
     u.username,
     u.email,
     u.createdAt,
     u.userProfile) FROM User u JOIN u.userProfile up WHERE up.id = :id
    """)
    Optional<EmployerDto> findEmployerById(UUID id);
}