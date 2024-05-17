package com.example.amongserver.observer;

import com.example.amongserver.dto.UserVoteDto;

public interface UserVoteDtoObserver {
    void update(UserVoteDto userVoteDto);
}
