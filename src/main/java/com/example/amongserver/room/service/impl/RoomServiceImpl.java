package com.example.amongserver.room.service.impl;

import com.example.amongserver.room.controller.dto.CreateRoomRequestDto;
import com.example.amongserver.room.controller.dto.CreateRoomResponseDto;
import com.example.amongserver.room.repository.RoomRepository;
import com.example.amongserver.room.repository.UserInGameRepository;
import com.example.amongserver.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UserInGameRepository userInGameRepository;
    @Override
    public CreateRoomResponseDto createRoom(CreateRoomRequestDto createRoomRequestDto) {
        return null;
    }
}
