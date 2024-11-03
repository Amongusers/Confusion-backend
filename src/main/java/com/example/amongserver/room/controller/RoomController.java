package com.example.amongserver.room.controller;

import com.example.amongserver.room.controller.dto.CreateRoomRequestDto;
import com.example.amongserver.room.controller.dto.CreateRoomResponseDto;
import com.example.amongserver.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    CreateRoomResponseDto createRoom(CreateRoomRequestDto createRoomRequestDto) {
        return roomService.createRoom(createRoomRequestDto);
    }
}
