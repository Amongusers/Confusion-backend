//package com.example.amongserver.service.script;
//
//import com.example.amongserver.domain.entity.GameState;
//import com.example.amongserver.domain.entity.User;
//import com.example.amongserver.reposirory.GemaStateRepository;
//import com.example.amongserver.reposirory.StartCoordinatesRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class GameOneStartScript implements CommandLineRunner {
//    private final GemaStateRepository repository;
//    private GameState gemaState ;
//
//    @Autowired
//    public GameOneStartScript(GemaStateRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        GameState gemaState = new GameState(false, false, null);
//        repository.save(gemaState);
//    }
//}
