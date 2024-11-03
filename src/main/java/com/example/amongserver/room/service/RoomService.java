package com.example.amongserver.room.service;

import com.example.amongserver.room.controller.dto.CreateRoomRequestDto;
import com.example.amongserver.room.controller.dto.CreateRoomResponseDto;

public interface RoomService {

    CreateRoomResponseDto createRoom(CreateRoomRequestDto createRoomRequestDto);
}
