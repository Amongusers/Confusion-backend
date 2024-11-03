package com.example.amongserver.room.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInGameAdminResponseDto {

    private Long id;

    private byte isAdmin;

    private String username;

    private String color;
}

