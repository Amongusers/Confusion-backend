package com.example.amongserver.registration.domain;

import com.example.amongserver.auother.domain.BaseEntityWithAudit;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "app_user", uniqueConstraints = @UniqueConstraint(columnNames = "user_email"))
@SequenceGenerator(name = "id_seq_base_with_id", sequenceName = "user_id_sequence", allocationSize = 1)
@EntityListeners({AuditingEntityListener.class})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "user_id")),
        @AttributeOverride(name = "createdBy", column = @Column(name = "user_create_user_id")),
        @AttributeOverride(name = "createdDate", column = @Column(name = "user_create_date")),
        @AttributeOverride(name = "lastModifiedBy", column = @Column(name = "user_update_user_id")),
        @AttributeOverride(name = "lastModifiedDate", column = @Column(name = "user_update_date")),
        @AttributeOverride(name = "deletedBy", column = @Column(name = "user_delete_user_id")),
        @AttributeOverride(name = "deletedDate", column = @Column(name = "user_delete_date")),
        @AttributeOverride(name = "isDeleted", column = @Column(name = "user_is_deleted"))
})
public class User extends BaseEntityWithAudit implements UserDetails {

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
