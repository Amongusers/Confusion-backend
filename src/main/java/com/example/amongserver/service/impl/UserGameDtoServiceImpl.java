package com.example.amongserver.service.impl;


import com.example.amongserver.domain.entity.User;
import com.example.amongserver.dto.UserGameDto;
import com.example.amongserver.dto.UserVoteDto;
import com.example.amongserver.mapper.UserMapper;
import com.example.amongserver.reposirory.UserRepository;
import com.example.amongserver.service.UserGameDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserGameDtoServiceImpl implements UserGameDtoService {
    private final UserRepository userRepository;



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
        boolean anyVotes = users.stream().anyMatch(u -> u.getNumberVotes() > 0);

        // Если хотя бы один пользователь проголосовал, запускаем таймер
        if (anyVotes) {
            Timer timer = new Timer();
            AtomicReference<UserGameDto> result = new AtomicReference<>(null); // Переменная для сохранения результата

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // По истечению времени таймера проверяем, все ли проголосовали
                    int numberVotes  = 0;
                    for (User user : users) {
                        numberVotes += user.getNumberVotes();
                    }
                    boolean allVoted = users.size() == numberVotes;
                    if (allVoted) {
                        // Если все проголосовали, выбираем пользователя с максимальным количеством голосов
                        User maxVotedUser = users.stream()
                                .max(Comparator.comparing(User::getNumberVotes))
                                .orElse(null);

                        // Преобразуем выбранного пользователя в UserGameDto и сохраняем его в переменной result
                        if (maxVotedUser != null) {
                            result.set(UserMapper.toUserGameGto(maxVotedUser));
                        } else {
                            result.set(null);
                        }
                    }
                    // Здесь нет необходимости возвращать значение, так как мы сохраняем результат в переменной
                }
            }, 60000); // 1 минута

            // Возвращаем результат, который был сохранен в переменной
            return result.get();
        }

        return null; // Если нет голосов, возвращаем null
    }

}