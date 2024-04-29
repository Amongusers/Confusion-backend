package com.example.amongserver.controller.restcontroller;

import com.example.amongserver.dto.UserGameDto;
import com.example.amongserver.service.UserGameDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserGameDtoService service;


    @PostMapping("/user")
    public UserGameDto createUser(@RequestBody UserGameDto userGameDto) {
        return service.add(userGameDto);
    }
    @GetMapping("/user")
    public List<UserGameDto> getAllUsers() {
        return service.getAll();
    }
    @GetMapping("/user/{id}")
    public UserGameDto getById(@PathVariable long id) {
        return service.getById(id);
    }
}
