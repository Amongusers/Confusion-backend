package com.example.amongserver.room.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinRoomResponseDto {

    private String secretCode;

    private double radius;

    private double latitude;

    private double longitude;

    private UserInGameAdminResponseDto userInGame;

    private List<UserInGameResponseDto> userInGameOtherList;

}
