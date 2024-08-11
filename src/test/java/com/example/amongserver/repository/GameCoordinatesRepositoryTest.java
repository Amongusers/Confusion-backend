package com.example.amongserver.repository;

import com.example.amongserver.domain.entity.User;
import com.example.amongserver.reposirory.UserRepository;
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
    private UserRepository userRepository;
    @Test
    void saveUser_whenValidData_userIsSaved() {
        // Arrange
        User user = new User("testUser", false);

        // Act
        User savedUser = userRepository.save(user);

        // Assert
        assertNotNull(savedUser.getId());
        assertEquals("testUser", savedUser.getLogin());
        assertFalse(savedUser.isDead());
    }

    @Test
    void findById_whenUserExists_userIsReturned() {
        // Arrange
        User user = new User("testUser", false);
        User savedUser = userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(savedUser.getId(), foundUser.get().getId());
        assertEquals("testUser", foundUser.get().getLogin());
    }

    @Test
    void findById_whenUserDoesNotExist_emptyOptionalIsReturned() {
        // Act
        Optional<User> foundUser = userRepository.findById(999L);

        // Assert
        assertTrue(foundUser.isEmpty());
    }

    @Test
    void findById_shouldThrowSQLException() throws SQLException {
        // Настроить поведение репозитория
        when(userRepository.findById(anyLong())).thenThrow(new SQLException("Database error"));

        // Проверить, что метод репозитория выбрасывает SQLException
        assertThrows(SQLException.class, () -> userRepository.findById(1L));
    }
}
