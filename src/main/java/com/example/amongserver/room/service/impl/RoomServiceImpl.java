package com.example.amongserver.room.service.impl;

import com.example.amongserver.auother.auditor.AuditorAwareImpl;
import com.example.amongserver.auother.exception.UserNotFoundException;
import com.example.amongserver.domain.Room;
import com.example.amongserver.domain.User;
import com.example.amongserver.domain.UserInGame;
import com.example.amongserver.registration.repository.UserRepository;
import com.example.amongserver.registration.service.impl.UserDetailsServiceImpl;
import com.example.amongserver.room.controller.dto.CreateRoomRequestDto;
import com.example.amongserver.room.controller.dto.CreateRoomResponseDto;
import com.example.amongserver.room.exception.UniqueSecretCodeNotGeneratedException;
import com.example.amongserver.room.exception.UserInContentNotFoundException;
import com.example.amongserver.room.mapper.RoomMapper;
import com.example.amongserver.room.repository.RoomRepository;
import com.example.amongserver.room.repository.UserInGameRepository;
import com.example.amongserver.room.service.RoomService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final RoomMapper roomMapper;
    @Override
    @Transactional
    public CreateRoomResponseDto createRoom(@NotNull final CreateRoomRequestDto createRoomRequestDto) {
        Room room = roomMapper.toRoomEntity(createRoomRequestDto);

        // Генерируем уникальный код
        String secretCode;
        List<String> secretCodeList;
        int attempts = 0;
        final int maxAttempts = 100; // Максимальное количество попыток
        do {
            if (attempts >= maxAttempts) {
                throw new UniqueSecretCodeNotGeneratedException("Unable to generate a unique secret code after " + maxAttempts + " attempts.");
            }
            secretCode = generateSecretCode();
            secretCodeList = roomRepository.findAllSecretCodes();
            attempts++;
        } while (secretCodeList.contains(secretCode));


        room.setSecretCode(secretCode);
        room.setGameState(0);


        // Получение текущего пользователя
        String email = getCurrentUserEmail();
        User user = (User) userDetailsService.loadUserByUsername(email);

        UserInGame userInGame = UserInGame.builder()
                .user(user)
                .room(room)
                .isAdmin((byte) 1)
                // TODO: нужно будет добавить метод для получения координат
                .latitude(0)
                .longitude(0)
                .color("red")
                .build();

        Set<UserInGame> userInGameSet = new HashSet<>();
        userInGameSet.add(userInGame);

        room.setUserInGameSet(userInGameSet);

        return roomMapper.toCreateRoomResponseDto(roomRepository.save(room));

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

    private String generateSecretCode() {
        // Метод генерации уникального кода, например UUID
        return UUID.randomUUID().toString();
    }
}
