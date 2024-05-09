package com.example.amongserver.controller.restcontroller;

import com.example.amongserver.dto.UserGameDto;
import com.example.amongserver.service.UserGameDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserGameDtoService service;


    @PostMapping()
    public UserGameDto createUser(@RequestBody UserGameDto userGameDto) {
        return service.add(userGameDto);
    }
    @GetMapping()
    public List<UserGameDto> getAllUsers() {
        return service.getAllIsDead();
    }
}
