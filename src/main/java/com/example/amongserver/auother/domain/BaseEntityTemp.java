package com.example.amongserver.auother.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


// TODO: возможно стоит переопредить методы object
@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class BaseEntityTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq_base_temp")
    @Column(name = "id")
    private Long id;
}
