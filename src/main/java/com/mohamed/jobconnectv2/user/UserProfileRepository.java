package com.mohamed.jobconnectv2.user;

import com.mohamed.jobconnectv2.user.dto.EmployerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {

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