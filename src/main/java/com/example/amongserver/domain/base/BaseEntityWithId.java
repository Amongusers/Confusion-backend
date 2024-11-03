package com.example.amongserver.domain.base;

import lombok.*;

import javax.persistence.*;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class BaseEntityWithId {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq_base_with_id")
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;
}
