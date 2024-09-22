package com.example.amongserver.domain.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@ToString
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    @SequenceGenerator(name = "role_id_seq", sequenceName = "role_id_sequence", allocationSize = 1)
    @Column(name = "role_id")
    private Long id;

    @Column (name = "role_class")
    private int roleClass;

    @Column (name = "role_type")
    private int type;
}
