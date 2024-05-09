package com.example.amongserver.service;

import com.example.amongserver.dto.UserVoteDto;

public interface UserVoteDtoService {

    // Голосование
    // Используется в VoteController
    // Выбирает пользователя с наибольшим количеством голосов и возвращает на клиент
    UserVoteDto vote(UserVoteDto userVoteDto);

    // Проверяет было ли начато голосование
    // Используется в VoteController
    boolean isVoteCanceled();
    void setVoteCanceled(boolean isCanceled);
}
