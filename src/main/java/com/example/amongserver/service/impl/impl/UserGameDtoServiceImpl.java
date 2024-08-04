package com.example.amongserver.service.impl.impl;


import com.example.amongserver.domain.entity.GameState;
import com.example.amongserver.domain.entity.User;
import com.example.amongserver.dto.GameStateDto;
import com.example.amongserver.dto.UserGameDto;
import com.example.amongserver.dto.UserKillDtoRequest;
import com.example.amongserver.exception.UserAlreadyDeadException;
import com.example.amongserver.exception.UserNotFoundException;
import com.example.amongserver.listener.GameStateChangedEvent;
import com.example.amongserver.mapper.GameStateMapper;
import com.example.amongserver.mapper.UserGameMapper;
import com.example.amongserver.reposirory.GameStateRepository;
import com.example.amongserver.reposirory.UserRepository;
import com.example.amongserver.service.impl.UserGameDtoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserGameDtoServiceImpl implements UserGameDtoService {
    private final UserRepository userRepository;
    private final GameStateRepository gameStateRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public UserGameDto add(UserGameDto userGameDto) {
        User user = UserGameMapper.toUserEntity(userGameDto);
        user.setReady(false);
        user.setIsImposter(null);
        user.setLatitude(null);
        user.setLongitude(null);
        user.setDead(false);
        user.setNumberVotes(0);
        //TODO: хз надо ли так
        user.setNumberVotes(0);
        GameState gameState = gameStateRepository.getById(1L);
        user.setGameState(gameState);

        return UserGameMapper.toUserGameDto(userRepository.save(user));
    }


    @Override
    public List<UserGameDto> updateUser(UserGameDto userGameDto) {
        Long id = userGameDto.getId();
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User with ID " + id + " not found");
        }
        User userDB = userOptional.get();
        userDB.setReady(userGameDto.isReady());
        userRepository.save(userDB);
        List<User> userList = allReady();
        return userList.stream().map(UserGameMapper::toUserGameDto).collect(Collectors.toList());
    }

    private List<User> allReady() {
        List<User> userList = userRepository.findAll();
        boolean isAllReady = true;
        for (User user : userList) {
            if (!user.isReady()) {
                isAllReady = false;
                break;
            }
        }
        if (!isAllReady /*|| userList.size() < 3*/) {
            return userRepository.saveAll(userList);
        } else {
            Optional<GameState> gameStateOptional = gameStateRepository.findById(1L);
            if (gameStateOptional.isPresent()) {
                GameState gameState = gameStateOptional.get();
                if (gameState.getGameState() != 1) {
                    gameState.setGameState(1);
                    GameStateDto gameStateDto = GameStateMapper.toGameStateGto(gameStateRepository.save(gameState));
                    GameStateChangedEvent event = new GameStateChangedEvent(this, gameStateDto);
                    eventPublisher.publishEvent(event);
                }
            }


            return createRole(userList);
        }
    }

    private List<User> createRole(List<User> userList) {
        for (User localUser : userList) {
            localUser.setIsImposter(false);
        }
        /*if (!userList.isEmpty()) {
            int impostorIndex = (int) (Math.random() * userList.size());
            userList.get(impostorIndex).setIsImposter(true);
            return userRepository.saveAll(userList);
        }*/
        int impostorIndex = (int) (Math.random() * userList.size());
        userList.get(impostorIndex).setIsImposter(true);
        return userRepository.saveAll(userList);
    }


    @Override
    public List<UserGameDto> getAll() {
        return userRepository.findAll().stream().map(UserGameMapper::toUserGameDto).collect(Collectors.toList());
    }

    @Override
    public void killUser(UserKillDtoRequest userKillDtoRequest) {
        User userDBDead = userRepository.findById(userKillDtoRequest
                        .getIdDead())
                .orElseThrow(() -> new UserNotFoundException("User with ID "
                        + userKillDtoRequest.getIdDead()
                        + " not found"));
        userRepository.findById(userKillDtoRequest
                        .getIdKiller())
                .orElseThrow(() -> new UserNotFoundException("User with ID "
                        + userKillDtoRequest.getIdKiller()
                        + " not found"));

        if (userDBDead.isDead())
            throw new UserAlreadyDeadException("User with ID "
                    + userKillDtoRequest.getIdDead()
                    + " already dead");
        userDBDead.setDead(true);
        userRepository.save(userDBDead);
        log.debug("User " + userDBDead.getLogin() + " is dead");

//        List<User> userListNotDead = userRepository.findAll().stream().filter(user -> !user.isDead()).toList();
//        long imposterCount = userListNotDead.stream().filter(User::getIsImposter).count();
//        long notImposterCount = userListNotDead.size() - imposterCount;
//
//        Optional<GameState> gameStateOptional = gameStateRepository.findById(1L);
//        if (gameStateOptional.isPresent()) {
//            GameState gameState = gameStateOptional.get();
//            if ((gameState.getGameState() == 1 || gameState.getGameState() == 2) && notImposterCount == 0) {
//                gameState.setGameState(4);
//                deleteAllUsers(gameState);
//                GameStateDto gameStateDto = GameStateMapper.toGameStateGto(gameStateRepository.save(gameState));
//                GameStateChangedEvent event = new GameStateChangedEvent(this, gameStateDto);
//                eventPublisher.publishEvent(event);
//
//            }
//        }
    }

    private void deleteAllUsers(GameState gameState) {
        userRepository.deleteAll(gameState.getUserList());
    }

}