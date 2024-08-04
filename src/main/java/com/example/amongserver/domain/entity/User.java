package com.example.amongserver.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
/*
Entity класс User
Сущность, которая хранится в БД
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "login")
    private String login;

    @Column (name = "is_ready")
    private boolean isReady;

    @Column (name = "is_imposter")
    private Boolean isImposter;

    @Column (name = "number_votes")
    private Integer numberVotes;

    @Column (name = "latitude")
    private Double latitude;

    @Column (name = "longitude")
    private Double longitude;

    @Column (name = "is_dead")
    private boolean isDead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_state_id")
    private GameState gameState;


    public User(String login, boolean isReady, Boolean isImposter) {
        this.login = login;
        this.isReady = isReady;
        this.isImposter = isImposter;
    }

    public User(int numberVotes) {
        this.numberVotes = numberVotes;
    }

    public User(String login, boolean isReady, Boolean isImposter, int numberVotes) {
        this.login = login;
        this.isReady = isReady;
        this.isImposter = isImposter;
        this.numberVotes = numberVotes;
    }
    public User(String login, boolean isDead) {
        this.login = login;
        this.isDead = isDead;
    }
}
