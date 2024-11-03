package com.example.amongserver.room.service.impl;

import com.example.amongserver.domain.Room;
import com.example.amongserver.repository.RoomRepository;
import com.example.amongserver.room.controller.dto.JoinRoomRequestDto;
import com.example.amongserver.room.controller.dto.JoinRoomResponseDto;
import com.example.amongserver.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private RoomRepository roomRepository;
    private UserDetailsService userDetailsService;
    @Override
    public JoinRoomResponseDto joinRoom(JoinRoomRequestDto joinRoomRequestDto) {
        String secretCode = joinRoomRequestDto.getSecretCode();
        Optional<Room> roomOptional = roomRepository.findBySecretCode(secretCode);
        if (roomOptional.isEmpty()) {
            throw new RuntimeException();
        }
        Room room = roomOptional.get();


    }
}
