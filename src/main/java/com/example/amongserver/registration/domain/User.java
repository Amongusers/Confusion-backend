package com.example.amongserver.registration.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user", uniqueConstraints = @UniqueConstraint(columnNames = "user_email"))
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "user_id")
    private Long id;

    @Column (name = "user_username", nullable = false)
    private String username;

    @Column (name = "user_email", nullable = false, unique = true)
    private String email;

    @Column (name = "user_password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "app_user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "aut_id")
    )
    private Set<Authority> authorities;

    @Column(name = "created_user", nullable = false, updatable = false)
    private LocalDateTime createdUser;

    @Column(name = "updated_user")
    private LocalDateTime updatedUser;

    @PrePersist
    protected void onCreate() {
        createdUser = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedUser = LocalDateTime.now();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
