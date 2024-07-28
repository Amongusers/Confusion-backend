package com.example.amongserver.controller.restcontroller;

import com.example.amongserver.dto.UserGameDto;
import com.example.amongserver.dto.UserVoteDto;
import com.example.amongserver.service.UserGameDtoService;
import com.example.amongserver.service.UserVoteDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*
Rest контроллер
Используется для регестрации пользователей
и получения списка всех "живых" пользователей перед голосованием
*/
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserGameDtoService userGameDtoService;
    private final UserVoteDtoService userVoteDtoService;


    @PostMapping()
    public UserGameDto createUser(@RequestBody UserGameDto userGameDto) {
        return userGameDtoService.add(userGameDto);
    }
    @GetMapping()
    public List<UserGameDto> getAllUsers() {
        return userVoteDtoService.getAllIsDead();
    }

    @PostMapping("/dead")
    public void deadUser(@RequestBody UserVoteDto userVoteDto) {
        userGameDtoService.deadUser(userVoteDto);
    }
}
