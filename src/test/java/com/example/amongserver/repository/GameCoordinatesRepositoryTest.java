package com.example.amongserver.repository;

import com.example.amongserver.domain.entity.UserLast;
import com.example.amongserver.reposirory.UserLastRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

//replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED тесты со встроеной БД
//replace = AutoConfigureTestDatabase.Replace.NONE тесты с подключенной БД
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GameCoordinatesRepositoryTest {

    @Autowired
    private UserLastRepository userLastRepository;
    @Test
    void saveUser_whenValidData_userIsSaved() {
        // Arrange
        UserLast userLast = new UserLast("testUser", false);

        // Act
        UserLast savedUserLast = userLastRepository.save(userLast);

        // Assert
        assertNotNull(savedUserLast.getId());
        assertEquals("testUser", savedUserLast.getLogin());
        assertFalse(savedUserLast.isDead());
    }

    @Test
    void findById_whenUserExists_userIsReturned() {
        // Arrange
        UserLast userLast = new UserLast("testUser", false);
        UserLast savedUserLast = userLastRepository.save(userLast);

        // Act
        Optional<UserLast> foundUser = userLastRepository.findById(savedUserLast.getId());

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(savedUserLast.getId(), foundUser.get().getId());
        assertEquals("testUser", foundUser.get().getLogin());
    }

    @Test
    void findById_whenUserDoesNotExist_emptyOptionalIsReturned() {
        // Act
        Optional<UserLast> foundUser = userLastRepository.findById(999L);

        // Assert
        assertTrue(foundUser.isEmpty());
    }

    @Test
    void findById_shouldThrowSQLException() throws SQLException {
        // Настроить поведение репозитория
        when(userLastRepository.findById(anyLong())).thenThrow(new SQLException("Database error"));

        // Проверить, что метод репозитория выбрасывает SQLException
        assertThrows(SQLException.class, () -> userLastRepository.findById(1L));
    }
}
