package com.mohamed.jobconnectv2.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    @Query("""
    SELECT r FROM Role r WHERE r.name = :roleName
    """)
    Optional<Role> findByName(String roleName);
}