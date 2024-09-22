package com.example.amongserver.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@ToString
@Table(name = "role")
// TODO: нужно ли наследование?
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    @SequenceGenerator(name = "role_id_seq", sequenceName = "role_id_sequence", allocationSize = 1)
    @Column(name = "role_id")
    private Long id;

    // TODO : что это?
    @Column (name = "role_class", nullable = false, unique = true)
    private int roleClass;

    @Column (name = "role_type")
    private int type;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<UserInGame> userInGameSet;
}
