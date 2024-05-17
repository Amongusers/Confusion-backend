package com.example.amongserver.service;

import com.example.amongserver.dto.UserGameDto;
import com.example.amongserver.dto.UserVoteDto;
import com.example.amongserver.observer.UserVoteDtoObserver;

import java.util.List;

public interface UserVoteDtoService {

    // Голосование
    // Используется в VoteController
    // Выбирает пользователя с наибольшим количеством голосов и возвращает на клиент
    void vote(UserVoteDto userVoteDto);

    // Проверяет было ли начато голосование
    // Используется в VoteController
    boolean isVoteCanceled();
    void setVoteCanceled(boolean isCanceled);

    // Очистка голосовани
    // Используется в VoteController
    void resetNumberVotes();

    // Регестрация слушателей
    void addObserver(UserVoteDtoObserver observer);


    // получение списка всех живых пользователей
    // используется в UserRestController
    List<UserGameDto> getAllIsDead();
}
