package com.mohamed.jobconnectv2.role;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "_role")
@Getter
@Setter
public class Role implements GrantedAuthority {
    @Id
    private UUID id;
    private String name;
    private LocalDateTime createdAt;

    @Override
    public String getAuthority() {
        return name;
    }
}
