package com.example.amongserver.authorization.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ на авторизацию")
public class UserAuthResponseDto {

    @Schema(description = "Токен", required = true)
    private String token;

    @Schema(description = "Ник", required = true)
    private String username;

    @Schema(description = "Почта", required = true)
    private String email;
}
