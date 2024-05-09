package com.example.amongserver.controller.restcontroller;


import com.example.amongserver.domain.entity.GameCoordinates;
import com.example.amongserver.service.GameCoordinatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("startCoordinates")
@RequiredArgsConstructor
public class StartCoordinatesRestController {

    private final GameCoordinatesService startCoordinatesService;



    @GetMapping()
    public List<GameCoordinates> getAllUsers() {
        return startCoordinatesService.getAll();
    }
}
