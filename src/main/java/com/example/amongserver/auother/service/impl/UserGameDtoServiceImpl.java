package com.example.amongserver.auother.service.impl;


import com.example.amongserver.auother.domain.GameState;
import com.example.amongserver.auother.domain.UserLast;
import com.example.amongserver.auother.dto.GameStateDto;
import com.example.amongserver.auother.dto.UserGameDto;
import com.example.amongserver.auother.dto.UserKillRequestDto;
import com.example.amongserver.auother.exception.UserAlreadyDeadException;
import com.example.amongserver.auother.exception.UserNotFoundException;
import com.example.amongserver.auother.listener.GameStateChangedEvent;
import com.example.amongserver.auother.mapper.GameStateMapper;
import com.example.amongserver.auother.mapper.UserGameMapper;
import com.example.amongserver.auother.reposirory.GameStateRepository;
import com.example.amongserver.auother.reposirory.UserLastRepository;
import com.example.amongserver.auother.service.UserGameDtoService;
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
    private final UserLastRepository userLastRepository;
    private final GameStateRepository gameStateRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public UserGameDto add(UserGameDto userGameDto) {
        UserLast userLast = UserGameMapper.toUserEntity(userGameDto);
        userLast.setReady(false);
        userLast.setIsImposter(null);
        userLast.setLatitude(null);
        userLast.setLongitude(null);
        userLast.setDead(false);
        userLast.setNumberVotes(0);
        //TODO: хз надо ли так
        userLast.setNumberVotes(0);
        GameState gameState = gameStateRepository.getById(1L);
        userLast.setGameState(gameState);

        return UserGameMapper.toUserGameDto(userLastRepository.save(userLast));
    }


    @Override
    public List<UserGameDto> updateUser(UserGameDto userGameDto) {
        Long id = userGameDto.getId();
        Optional<UserLast> userOptional = userLastRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User with ID " + id + " not found");
        }
        UserLast userLastDB = userOptional.get();
        userLastDB.setReady(userGameDto.isReady());
        userLastRepository.save(userLastDB);
        List<UserLast> userLastList = allReady();
        return userLastList.stream().map(UserGameMapper::toUserGameDto).collect(Collectors.toList());
    }

    private List<UserLast> allReady() {
        List<UserLast> userLastList = userLastRepository.findAll();
        boolean isAllReady = true;
        for (UserLast userLast : userLastList) {
            if (!userLast.isReady()) {
                isAllReady = false;
                break;
            }
        }
        if (!isAllReady /*|| userList.size() < 3*/) {
            return userLastRepository.saveAll(userLastList);
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


            return createRole(userLastList);
        }
    }

    private List<UserLast> createRole(List<UserLast> userLastList) {
        for (UserLast localUserLast : userLastList) {
            localUserLast.setIsImposter(false);
        }
        /*if (!userList.isEmpty()) {
            int impostorIndex = (int) (Math.random() * userList.size());
            userList.get(impostorIndex).setIsImposter(true);
            return userRepository.saveAll(userList);
        }*/
        int impostorIndex = (int) (Math.random() * userLastList.size());
        userLastList.get(impostorIndex).setIsImposter(true);
        return userLastRepository.saveAll(userLastList);
    }


    @Override
    public List<UserGameDto> getAll() {
        return userLastRepository.findAll().stream().map(UserGameMapper::toUserGameDto).collect(Collectors.toList());
    }

    @Override
    public void killUser(UserKillRequestDto userKillRequestDto) {
        UserLast userLastDBDead = userLastRepository.findById(userKillRequestDto
                        .getIdDead())
                .orElseThrow(() -> new UserNotFoundException("User with ID "
                        + userKillRequestDto.getIdDead()
                        + " not found"));
        userLastRepository.findById(userKillRequestDto
                        .getIdKiller())
                .orElseThrow(() -> new UserNotFoundException("User with ID "
                        + userKillRequestDto.getIdKiller()
                        + " not found"));

        if (userLastDBDead.isDead())
            throw new UserAlreadyDeadException("User with ID "
                    + userKillRequestDto.getIdDead()
                    + " already dead");
        userLastDBDead.setDead(true);
        userLastRepository.save(userLastDBDead);
        log.debug("User " + userLastDBDead.getLogin() + " is dead");

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
        userLastRepository.deleteAll(gameState.getUserLastList());
    }

}