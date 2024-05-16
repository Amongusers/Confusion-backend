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

    private final UserGameDtoService userGameDtoService;


    @PostMapping()
    public UserGameDto createUser(@RequestBody UserGameDto userGameDto) {
        return userGameDtoService.add(userGameDto);
    }
    @GetMapping()
    public List<UserGameDto> getAllUsers() {
        return userGameDtoService.getAllIsDead();
    }
}
