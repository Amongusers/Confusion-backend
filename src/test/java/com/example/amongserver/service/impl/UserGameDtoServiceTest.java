package com.example.amongserver.service.impl;

import com.example.amongserver.domain.entity.User;
import com.example.amongserver.dto.UserKillDtoRequest;
import com.example.amongserver.exception.UserAlreadyDeadException;
import com.example.amongserver.exception.UserNotFoundException;
import com.example.amongserver.reposirory.UserRepository;
import com.example.amongserver.service.impl.impl.UserGameDtoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserGameDtoServiceTest {
    @InjectMocks
    private UserGameDtoServiceImpl userGameDtoService;
    @Mock
    private UserRepository userRepository;
    @Test
    void killUser_withValidData_saveUserIsDead() {
        // Arrange
        Long idDead = 1L;
        Long idKiller = 2L;
        UserKillDtoRequest request = new UserKillDtoRequest(idKiller, idDead);
        User deadUser = new User("deadUser", false); // Создаем пользователя с isDead = false
        User killerUser = new User( "killerUser", false); // Создаем пользователя с isDead = false

        // Настраиваем mock поведение
        when(userRepository.findById(idDead)).thenReturn(Optional.of(deadUser));
        when(userRepository.findById(idKiller)).thenReturn(Optional.of(killerUser));

        // Act
        userGameDtoService.killUser(request);

        // Assert
        assertTrue(deadUser.isDead());
        verify(userRepository, times(1)).save(deadUser);
    }

    @Test
    void killUser_withNonExistentDeadUser_throwsUserNotFoundException() {
        // Arrange
        Long idDead = 1L;
        Long idKiller = 2L;
        UserKillDtoRequest request = new UserKillDtoRequest(idKiller, idDead);

        // Настраиваем mock поведение
        when(userRepository.findById(idDead)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userGameDtoService.killUser(request));
    }

    @Test
    void killUser_withNonExistentKiller_throwsUserNotFoundException() {
        // Arrange
        Long idDead = 1L;
        Long idKiller = 2L;
        UserKillDtoRequest request = new UserKillDtoRequest(idKiller, idDead);
        User deadUser = new User( "deadUser", false);

        // Настраиваем mock поведение
        when(userRepository.findById(idDead)).thenReturn(Optional.of(deadUser));
        when(userRepository.findById(idKiller)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userGameDtoService.killUser(request));
    }

    @Test
    void killUser_whenUserAlreadyDead_throwsUserAlreadyDeadException() {
        // Arrange
        Long idDead = 1L;
        Long idKiller = 2L;
        UserKillDtoRequest request = new UserKillDtoRequest(idKiller, idDead);
        User deadUser = new User( "deadUser", true); // Пользователь уже мертв
        User killerUser = new User( "killerUser", false);

        // Настраиваем mock поведение
        when(userRepository.findById(idDead)).thenReturn(Optional.of(deadUser));
        when(userRepository.findById(idKiller)).thenReturn(Optional.of(killerUser));

        // Act & Assert
        assertThrows(UserAlreadyDeadException.class, () -> userGameDtoService.killUser(request));
    }
}
