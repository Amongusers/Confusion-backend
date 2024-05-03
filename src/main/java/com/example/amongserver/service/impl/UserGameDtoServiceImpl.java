package com.example.amongserver.service.impl;


import com.example.amongserver.domain.entity.User;
import com.example.amongserver.dto.UserGameDto;
import com.example.amongserver.dto.UserVoteDto;
import com.example.amongserver.mapper.UserMapper;
import com.example.amongserver.reposirory.UserRepository;
import com.example.amongserver.service.UserGameDtoService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserGameDtoServiceImpl implements UserGameDtoService {
    private final UserRepository userRepository;

    private boolean isVoteCansel;
    private final Timer timer = new Timer();

    @Override
    public boolean isVoteCanceled() {
        return isVoteCansel;
    }

    @Override
    public UserGameDto add(UserGameDto userGameDto) {
        User user = UserMapper.toUserEntity(userGameDto);
        user.setReady(false);
        user.setIsImposter(null);
        //TODO: хз надо ли так
        user.setNumberVotes(0);

        return UserMapper.toUserGameGto(userRepository
                .save(user));
    }

    @Override
    public List<UserGameDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserGameGto)
                .collect(Collectors.toList());
    }

    @Override
    public UserGameDto getById(long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new RuntimeException("User with ID " + id + " not found");

        return UserMapper.toUserGameGto(user.get());
    }

    @Override
    public List<UserGameDto> addAll(List<UserGameDto> userGameDtoList) {
        List<User> userList = userGameDtoList.stream()
                .map(UserMapper::toUserEntity)
                .toList();
        return userRepository.saveAll(userList)
                .stream()
                .map(UserMapper::toUserGameGto)
                .collect(Collectors.toList());
    }

    @Override
    public UserGameDto update(long id, UserGameDto userGameDto) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) throw new RuntimeException("User with ID " + id + " not found");

        User user = userOptional.get();
        if (userGameDto.getLogin() != null) user.setLogin(userGameDto.getLogin());
        user.setReady(userGameDto.isReady());
        if (userGameDto.getIsImposter() != null) user.setIsImposter(userGameDto.getIsImposter());

        return UserMapper.toUserGameGto(userRepository.save(user));
    }

    @Override
    public UserGameDto vote(UserGameDto userGameDto) {
        Optional<User> userOptional = userRepository.findById(userGameDto.getId());

        if (userOptional.isEmpty()) throw new RuntimeException("User with ID " + userGameDto.getId() + " not found");

        User userDB = userOptional.get();
        userDB.setNumberVotes(userDB.getNumberVotes()+1);
        userRepository.save(userDB);

        // Проверяем, есть ли пользователи, у которых уже были голоса
        List<User> users = userRepository.findAll();

        int totalVotes = users.stream().mapToInt(User::getNumberVotes).sum();

        // Если хотя бы один пользователь проголосовал, запускаем таймер

        if (totalVotes==1) {

            AtomicReference<UserGameDto> result = new AtomicReference<>(null); // Переменная для сохранения результата

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



    private UserGameDto getUserGameDto(List<User> users) {
        User maxVotedUser = users.stream()
                .max(Comparator.comparing(User::getNumberVotes))
                .orElse(null);

        // Преобразуем выбранного пользователя в UserGameDto и сохраняем его в переменной result
        if (maxVotedUser != null) {
            return UserMapper.toUserGameGto(maxVotedUser);
        } else {
            return null;
        }
    }
}