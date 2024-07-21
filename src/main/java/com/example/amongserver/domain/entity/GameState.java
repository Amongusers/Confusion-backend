package com.example.amongserver.domain.entity;

import com.example.amongserver.listener.GameStateListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
/*
Entity класс GameState
Сущность, которая хранится в БД
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(GameStateListener.class)
@Table(name = "game_state")
public class GameState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_state")
    private int gameState;

    @OneToMany(mappedBy = "gameState", cascade = CascadeType.ALL)
    private List<User> userList;

    public GameState(int gameState) {
        this.gameState = gameState;
    }
}
