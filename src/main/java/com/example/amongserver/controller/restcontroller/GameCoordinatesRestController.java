package com.example.amongserver.controller.restcontroller;


import com.example.amongserver.domain.entity.GameCoordinates;
import com.example.amongserver.dto.GameCoordinatesDto;
import com.example.amongserver.service.GameCoordinatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("startCoordinates")
@RequiredArgsConstructor
public class GameCoordinatesRestController {

    private final GameCoordinatesService gameCoordinatesService;



    @GetMapping()
    public List<GameCoordinatesDto> getAllUsers() {
        return gameCoordinatesService.getAll();
    }
}
