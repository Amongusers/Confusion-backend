package com.example.amongserver.controller.restcontroller;

import com.example.amongserver.dto.GameStateDto;
import com.example.amongserver.service.GameStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gameStateStart")
@RequiredArgsConstructor
public class GameStateRestController {

    private final GameStateService gameStateService;

    @GetMapping()
    public GameStateDto getGameState() {
        return gameStateService.getGameState();
    }
}
