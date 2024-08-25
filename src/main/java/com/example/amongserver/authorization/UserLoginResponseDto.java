package com.example.amongserver.authorization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponseDto {
    private Long id;
    private String username;
    private String email;
    // Допустим, добавим поле для JWT-токена, если вы его используете
    private String token;

    public UserLoginResponseDto(String jwtToken) {
        this.token = jwtToken;
    }
}
