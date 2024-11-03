package com.example.amongserver.room.controller;

import com.example.amongserver.room.controller.dto.JoinRoomRequestDto;
import com.example.amongserver.room.controller.dto.JoinRoomResponseDto;
import com.example.amongserver.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;


    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public JoinRoomResponseDto joinRoom(@RequestBody JoinRoomRequestDto joinRoomRequestDto) {
        return roomService.joinRoom(joinRoomRequestDto);
    }


}
