package com.example.amongserver.room.service;

import com.example.amongserver.room.controller.dto.JoinRoomRequestDto;
import com.example.amongserver.room.controller.dto.JoinRoomResponseDto;

public interface RoomService{

    JoinRoomResponseDto joinRoom(JoinRoomRequestDto joinRoomRequestDto);
}
