package com.example.amongserver.service;

import com.example.amongserver.auother.domain.UserLast;
import com.example.amongserver.auother.dto.UserKillRequestDto;
import com.example.amongserver.auother.exception.UserAlreadyDeadException;
import com.example.amongserver.auother.exception.UserNotFoundException;
import com.example.amongserver.auother.reposirory.UserLastRepository;
import com.example.amongserver.auother.service.impl.UserGameDtoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserLastGameDtoServiceTest {
    @InjectMocks
    private UserGameDtoServiceImpl userGameDtoService;
    @Mock
    private UserLastRepository userLastRepository;
    @Test
    void killUser_withValidData_saveUserIsDead() {
        // Arrange
        Long idDead = 1L;
        Long idKiller = 2L;
        UserKillRequestDto request = new UserKillRequestDto(idKiller, idDead);
        UserLast deadUserLast = new UserLast("deadUser", false); // Создаем пользователя с isDead = false
        UserLast killerUserLast = new UserLast( "killerUser", false); // Создаем пользователя с isDead = false

        // Настраиваем mock поведение
        when(userLastRepository.findById(idDead)).thenReturn(Optional.of(deadUserLast));
        when(userLastRepository.findById(idKiller)).thenReturn(Optional.of(killerUserLast));

        // Act
        userGameDtoService.killUser(request);

        // Assert
        assertTrue(deadUserLast.isDead());
        verify(userLastRepository, times(1)).findById(idDead);
        verify(userLastRepository, times(1)).findById(idKiller);
        verify(userLastRepository, times(1)).save(deadUserLast);
    }

    @Test
    void killUser_withNonExistentDeadUser_throwsUserNotFoundException() {
        // Arrange
        Long idDead = 1L;
        Long idKiller = 2L;
        UserKillRequestDto request = new UserKillRequestDto(idKiller, idDead);

        // Настраиваем mock поведение
        when(userLastRepository.findById(idDead)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userGameDtoService.killUser(request));
        verify(userLastRepository, times(1)).findById(idDead);
        verify(userLastRepository, never()).findById(idKiller);
        verify(userLastRepository, never()).save(any(UserLast.class));
    }

    @Test
    void killUser_withNonExistentKiller_throwsUserNotFoundException() {
        // Arrange
        Long idDead = 1L;
        Long idKiller = 2L;
        UserKillRequestDto request = new UserKillRequestDto(idKiller, idDead);
        UserLast deadUserLast = new UserLast( "deadUser", false);

        // Настраиваем mock поведение
        when(userLastRepository.findById(idDead)).thenReturn(Optional.of(deadUserLast));
        when(userLastRepository.findById(idKiller)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userGameDtoService.killUser(request));
        verify(userLastRepository, times(1)).findById(idDead);
        verify(userLastRepository, times(1)).findById(idKiller);
        verify(userLastRepository, never()).save(any(UserLast.class));
    }

    @Test
    void killUser_whenUserAlreadyDead_throwsUserAlreadyDeadException() {
        // Arrange
        Long idDead = 1L;
        Long idKiller = 2L;
        UserKillRequestDto request = new UserKillRequestDto(idKiller, idDead);
        UserLast deadUserLast = new UserLast( "deadUser", true); // Пользователь уже мертв
        UserLast killerUserLast = new UserLast( "killerUser", false);

        // Настраиваем mock поведение
        when(userLastRepository.findById(idDead)).thenReturn(Optional.of(deadUserLast));
        when(userLastRepository.findById(idKiller)).thenReturn(Optional.of(killerUserLast));

        // Act & Assert
        assertThrows(UserAlreadyDeadException.class, () -> userGameDtoService.killUser(request));
        verify(userLastRepository, times(1)).findById(idDead);
        verify(userLastRepository, times(1)).findById(idKiller);
        verify(userLastRepository, never()).save(any(UserLast.class));
    }

//    @Test
//    void killUser_shouldHandleSQLException() throws SQLException {
//        Long idDead = 1L;
//        Long idKiller = 2L;
//        UserKillRequestDto request = new UserKillRequestDto(idKiller, idDead);
//
//        // Настроить репозиторий так, чтобы он выбрасывал SQLException
//        when(userLastRepository.findById(idDead)).thenThrow(new SQLException("Database error"));
//
//        // Проверить, что метод сервиса выбрасывает исключение при ошибке в репозитории
//        assertThrows(SQLException.class, () -> userGameDtoService.killUser(request));
//    }
}
