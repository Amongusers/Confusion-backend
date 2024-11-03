package com.example.amongserver.room.service.impl;

import com.example.amongserver.domain.Room;
import com.example.amongserver.domain.User;
import com.example.amongserver.domain.UserInGame;
import com.example.amongserver.repository.RoomRepository;
import com.example.amongserver.repository.UserInGameRepository;
import com.example.amongserver.repository.UserRepository;
import com.example.amongserver.room.controller.dto.JoinRoomRequestDto;
import com.example.amongserver.room.controller.dto.JoinRoomResponseDto;
import com.example.amongserver.room.controller.dto.UserInGameAdminResponseDto;
import com.example.amongserver.room.controller.dto.UserInGameResponseDto;
import com.example.amongserver.room.controller.exception.UserInContentNotFoundException;
import com.example.amongserver.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UserDetailsService userDetailsService;
    private final UserInGameRepository userInGameRepository;
    private final UserRepository userRepository;
    @Override
    public JoinRoomResponseDto joinRoom(JoinRoomRequestDto joinRoomRequestDto) {
        String secretCode = joinRoomRequestDto.getSecretCode();
        Optional<Room> roomOptional = roomRepository.findBySecretCode(secretCode);
        if (roomOptional.isEmpty()) {
            throw new RuntimeException();
        }
        Room room = roomOptional.get();

        String email = getCurrentUserEmail();
        User user = (User) userDetailsService.loadUserByUsername(email);
        UserInGame userInGame = UserInGame.builder()
                .user(user)
                .room(room)
                .longitude(0)
                .latitude(0)
                .isAdmin((byte) 0)
                .connectedStatus((byte) 1)
                .isDead(false)
                .color("red")
                .build();

        UserInGame userInGameSaved = userInGameRepository.save(userInGame);

        UserInGameAdminResponseDto userCurrent = UserInGameAdminResponseDto.builder()
                .id(userInGameSaved.getId())
                .isAdmin(userInGameSaved.getIsAdmin())
                .username(user.getUsername())
                .color(userInGameSaved.getColor())
                .build();

        List<UserInGameResponseDto> userOthersList = new ArrayList<>();
        for (UserInGame userInGameFor : room.getUserInGameSet()) {
            Optional<User> userOptional = userRepository.findById(userInGameFor.getId());
            if (userOptional.isEmpty()) {
                throw new RuntimeException();
            }
            User userFor = userOptional.get();
            UserInGameResponseDto userOthers = UserInGameResponseDto.builder()
                    .id(userInGameFor.getId())
                    .username(userFor.getUsername())
                    .color(userInGameFor.getColor())
                    .build();
        }

        return JoinRoomResponseDto.builder()
                .secretCode(room.getSecretCode())
                .radius(room.getRadius())
                .latitude(room.getLatitude())
                .longitude(room.getLatitude())
                .userInGame(userCurrent)
                .userInGameOtherList(userOthersList)
                .build();
    }

    private String getCurrentUserEmail() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        } else if (authentication != null) {
            return authentication.getPrincipal().toString();
        }
        throw new UserInContentNotFoundException("No authenticated user found in context");
    }
}
