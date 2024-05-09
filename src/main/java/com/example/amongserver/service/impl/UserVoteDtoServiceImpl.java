package com.example.amongserver.service.impl;

import com.example.amongserver.domain.entity.User;
import com.example.amongserver.dto.UserVoteDto;
import com.example.amongserver.mapper.UserVoteMapper;
import com.example.amongserver.reposirory.UserRepository;
import com.example.amongserver.service.UserVoteDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class UserVoteDtoServiceImpl implements UserVoteDtoService {
    private boolean isVoteCansel;
    private final Timer timer = new Timer();
    private final UserRepository userRepository;

    @Override
    public UserVoteDto vote(UserVoteDto userVoteDto) {
        Long id = userVoteDto.getId();
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) throw new RuntimeException("User with ID " + id + " not found");

        User userDB = userOptional.get();
        userDB.setNumberVotes(userDB.getNumberVotes()+1);
        userRepository.save(userDB);

        // Проверяем, есть ли пользователи, у которых уже были голоса
        List<User> users = userRepository.findAll();

        int totalVotes = users.stream().mapToInt(User::getNumberVotes).sum();

        // Если хотя бы один пользователь проголосовал, запускаем таймер

        if (totalVotes==1) {

            AtomicReference<UserVoteDto> result = new AtomicReference<>(null); // Переменная для сохранения результата

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isVoteCansel = true;
                    result.set(getUserGameDto(users));
                }
            }, 60000); // 1 минута

            // Возвращаем результат, который был сохранен в переменной
            return result.get();
        } else if (totalVotes==users.size()) {
            timer.cancel();
            isVoteCansel = true;
            return getUserGameDto(users);
        }  else {
            return null;
        }
    }

    private UserVoteDto getUserGameDto(List<User> users) {
        User maxVotedUser = users.stream()
                .max(Comparator.comparing(User::getNumberVotes))
                .orElse(null);

        // Преобразуем выбранного пользователя в UserGameDto и сохраняем его в переменной result
        if (maxVotedUser != null) {
            return UserVoteMapper.toUserVoteDto(maxVotedUser);
        } else {
            return null;
        }
    }

    @Override
    public boolean isVoteCanceled() {
        return isVoteCansel;
    }

    @Override
    public void setVoteCanceled(boolean isCanceled) {
        this.isVoteCansel=isCanceled;
    }
}
