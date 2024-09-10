package com.example.amongserver.authrization.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Ответ в случае ошибки на авторизацию")
public class UserAuthErrorResponseDto {
    @Schema(description = "Сообщение об ошибке", required = true)
    private String errorMessage;
}
