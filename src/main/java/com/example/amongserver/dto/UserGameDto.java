package com.example.amongserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
Класс транспортировки данных между клиентом и сервером
Dto нужен для ограничения данных, отправляемых на клиент
и для менее тесной связи клиента с БД
Используется в UserRestController и UserController
*/
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
