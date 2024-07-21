package com.example.amongserver.component;

import com.example.amongserver.domain.entity.GameState;
import com.example.amongserver.reposirory.GameStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
/*
Класс, заполняющий в БД игровую сессию
Выполняется при запуске сервера
*/
@Component
public class GameOneStartScript implements CommandLineRunner {
    private final GameStateRepository gemaStateRepository;

    @Autowired
    public GameOneStartScript(GameStateRepository repository) {
        this.gemaStateRepository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        GameState gemaState = new GameState(0);
        gemaStateRepository.save(gemaState);
    }
}
