package com.example.amongserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGameDto {

    private Long id;

    private String login;

    private boolean isReady;

    private Boolean isImposter;

}
