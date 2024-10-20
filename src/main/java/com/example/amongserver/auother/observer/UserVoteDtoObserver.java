package com.example.amongserver.auother.observer;

import com.example.amongserver.auother.dto.UserVoteDto;
/*
Observer для голосования, используется в VoteController и UserVoteDtoService
*/
public interface UserVoteDtoObserver {
    void update(UserVoteDto userVoteDto);
}
