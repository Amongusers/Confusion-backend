package com.example.amongserver.observer;

import com.example.amongserver.dto.UserVoteDto;
/*
Observer для голосования, используется в VoteController и UserVoteDtoService
*/
public interface UserVoteDtoObserver {
    void update(UserVoteDto userVoteDto);
}
