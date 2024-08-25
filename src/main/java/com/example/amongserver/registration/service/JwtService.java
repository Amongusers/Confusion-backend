package com.example.amongserver.registration.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    /**
     * Генерация JWT токена на основе email пользователя.
     *
     * @param userDetails - email пользователя
     * @return сгенерированный JWT токен
     */
    String generateToken(UserDetails userDetails);

    String extractUsername(String token);

    /**
     * Извлечение email (subject) из JWT токена.
     *
     * @param token - JWT токен
     * @return email пользователя
     */
    String getEmailFromToken(String token);

    /**
     * Валидация токена: проверка, не истек ли он, и совпадает ли email из токена с email пользователя.
     *
     * @param token - JWT токен
     * @param userDetails - информация о пользователе
     * @return true, если токен валиден, иначе false
     */
    boolean validateToken(String token, UserDetails userDetails);
}
