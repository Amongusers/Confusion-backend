package com.example.amongserver.auother.controller.restcontroller;


import com.example.amongserver.auother.dto.GameCoordinatesDto;
import com.example.amongserver.auother.service.GameCoordinatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/*
Rest контроллер
Получание всех координатат на клиент в начале игры
*/
@RestController
@RequestMapping("/startCoordinates")
@RequiredArgsConstructor
public class GameCoordinatesRestController {

    private final GameCoordinatesService gameCoordinatesService;



    @GetMapping()
    public List<GameCoordinatesDto> getAllUsers() {
        return gameCoordinatesService.getAll();
    }
}
