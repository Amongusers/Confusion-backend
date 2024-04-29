package com.example.amongserver.service.impl;


import com.example.amongserver.domain.entity.User;
import com.example.amongserver.dto.UserGameDto;
import com.example.amongserver.dto.UserVoteDto;
import com.example.amongserver.mapper.UserMapper;
import com.example.amongserver.reposirory.UserRepository;
import com.example.amongserver.service.UserGameDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
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
    public UserGameDto vote(long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) throw new RuntimeException("User with ID " + id + " not found");

        User user = userOptional.get();
        user.setNumberVotes(user.getNumberVotes()+1);
        userRepository.save(user);
        // TODO: таймер
        return UserMapper.toUserGameGto(user);

//        List<User> users = userRepository.findAll();
//        Timer timer = new Timer();
//        for (User user1 : users) {
//            if (user1.getNumberVotes()!=null) {
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        return ;
//                    }
//                }, 60000);
//                break;
//            }
//        }
//
//
//        boolean isAllVoting = true;
//        for (UserVoteDto userVoteDto : usersVoteDto) {
//            if (userVoteDto.getNumberVotes()==null) {
//                isAllVoting = false;
//                break;
//            }
//        }
//
    }

}