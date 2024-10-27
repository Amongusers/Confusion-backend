package com.example.amongserver.registration.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ в случае ошибки на регестрацию")
public class UserRegisterErrorResponseDto {
    @Schema(description = "Сообщение об ошибке", required = true)
    private String errorMessage;
}
