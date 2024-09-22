package com.example.amongserver.auother.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
Класс транспортировки данных между клиентом и сервером
Dto нужен для ограничения данных, отправляемых на клиент
и для менее тесной связи клиента с БД
Используется в VoteController
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVoteDto {

    private Long id;

    private String login;
}
