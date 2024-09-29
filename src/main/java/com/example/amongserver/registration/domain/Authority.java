package com.example.amongserver.registration.domain;

import com.example.amongserver.auother.domain.BaseEntityWithId;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "authority")
@SequenceGenerator(name = "id_seq_base_with_id", sequenceName = "aut_id_sequence", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "aut_id"))
public class Authority extends BaseEntityWithId implements GrantedAuthority {
    @Column (name = "aut_authority")
    private String authority;
}
