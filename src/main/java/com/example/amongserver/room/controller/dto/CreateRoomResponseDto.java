package com.example.amongserver.room.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomResponseDto {

    private String secretCode;

    private Double radius;

    private Double latitude;

    private Double longitude;

    private UserInGameAdminResponseDto userInGame;
}
