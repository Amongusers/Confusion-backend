package com.example.amongserver.auother.domain;

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
