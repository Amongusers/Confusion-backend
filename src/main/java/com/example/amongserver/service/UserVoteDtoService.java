package com.example.amongserver.service;

import com.example.amongserver.dto.UserGameDto;
import com.example.amongserver.dto.UserVoteDto;
import com.example.amongserver.observer.UserVoteDtoObserver;

import java.util.List;

/*
сервис голосования
*/
public interface UserVoteDtoService {

    // Голосование
    // Используется в VoteController
    // Выбирает пользователя с наибольшим количеством голосов и возвращает на клиент
    // url /vote WebSockets
    void vote(UserVoteDto userVoteDto);

    // Проверяет было ли начато голосование
    // Используется в VoteController
    // Не взаимодействует с клиентом
    boolean isVoteCanceled();
    // Не взаимодействует с клиентом
    void setVoteCanceled(boolean isCanceled);

    // Очистка голосовани
    // Используется в VoteController
    // Не взаимодействует с клиентом
    void resetNumberVotes();

    // Регестрация слушателей
    // Не взаимодействует с клиентом
    void addObserver(UserVoteDtoObserver observer);


    // получение списка всех живых пользователей
    // используется в UserRestController
    // url /user GET запрос
    List<UserGameDto> getAllIsDead();
}
