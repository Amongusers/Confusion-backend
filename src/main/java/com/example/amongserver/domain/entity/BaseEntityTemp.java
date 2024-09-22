package com.example.amongserver.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


// TODO: возможно стоит переопредить методы object
@MappedSuperclass
public class BaseEntityTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq_gen")
    private Long id;
}
