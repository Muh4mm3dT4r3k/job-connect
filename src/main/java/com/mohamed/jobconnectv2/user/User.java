package com.mohamed.jobconnectv2.user;

import com.mohamed.jobconnectv2.role.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "_user")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    @Id
    private UUID id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean isEnabled;
    private boolean isNonLooked;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @OneToOne
    private UserProfile userProfile;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(()-> role.getAuthority());
    }
}
