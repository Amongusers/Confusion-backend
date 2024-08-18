package com.example.amongserver.service.impl;

import com.example.amongserver.domain.entity.GameState;
import com.example.amongserver.domain.entity.UserLast;
import com.example.amongserver.dto.GameStateDto;
import com.example.amongserver.dto.UserGameDto;
import com.example.amongserver.dto.UserVoteDto;
import com.example.amongserver.listener.GameStateChangedEvent;
import com.example.amongserver.mapper.GameStateMapper;
import com.example.amongserver.mapper.UserGameMapper;
import com.example.amongserver.mapper.UserVoteMapper;
import com.example.amongserver.observer.UserVoteDtoObserver;
import com.example.amongserver.reposirory.GameStateRepository;
import com.example.amongserver.reposirory.UserLastRepository;
import com.example.amongserver.service.UserVoteDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserVoteDtoServiceImpl implements UserVoteDtoService {
    private boolean isVoteCansel;

    private final UserLastRepository userLastRepository;
    private final GameStateRepository gameStateRepository;
    private final ApplicationEventPublisher eventPublisher;
    private Timer timer;


    // Список наблюдателей
    private final List<UserVoteDtoObserver> observers = new ArrayList<>();

    // Метод для добавления наблюдателей
    @Override
    public void addObserver(UserVoteDtoObserver observer) {
        observers.add(observer);
    }

    // Метод для удаления наблюдателей
    public void removeObserver(UserVoteDtoObserver observer) {
        observers.remove(observer);
    }

    // Метод для уведомления наблюдателей
    private void notifyObservers(UserVoteDto userVoteDto) {
        for (UserVoteDtoObserver observer : observers) {
            observer.update(userVoteDto);
        }
    }


    @Override
    public List<UserGameDto> getAllIsDead() {
        Optional<GameState> gameStateOptional = gameStateRepository.findById(1L);
        if (gameStateOptional.isPresent()) {
            GameState gameState = gameStateOptional.get();
            if (gameState.getGameState() != 2) {
                timer = new Timer();
                System.out.println("Таймер запущен");
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isVoteCansel = true;
                        List<UserLast> userLasts = userLastRepository.findAll();
                        notifyObservers(getUserGameDto(userLasts));
                    }
                }, 60000); // 1 минута


                gameState.setGameState(2);
                GameStateDto gameStateDto = GameStateMapper.toGameStateGto(gameStateRepository.save(gameState));
                GameStateChangedEvent event = new GameStateChangedEvent(this, gameStateDto);
                eventPublisher.publishEvent(event);
            }
        }
        return userLastRepository.findAllByIsDead(false)
                .stream()
                .map(UserGameMapper::toUserGameDto)
                .collect(Collectors.toList());
    }






    @Override
    public void vote(UserVoteDto userVoteDto) {
        Long id = userVoteDto.getId();
        Optional<UserLast> userOptional = userLastRepository.findById(id);

        if (userOptional.isEmpty()) throw new RuntimeException("User with ID " + id + " not found");

        UserLast userLastDB = userOptional.get();
        userLastDB.setNumberVotes(userLastDB.getNumberVotes()+1);
        userLastRepository.save(userLastDB);

        // Проверяем, есть ли пользователи, у которых уже были голоса
        List<UserLast> userLasts = userLastRepository.findAll();

        int totalVotes = userLasts.stream().mapToInt(UserLast::getNumberVotes).sum();

        // Если хотя бы один пользователь проголосовал, запускаем таймер

        if (totalVotes== userLasts.size()) {
            timer.cancel();
            isVoteCansel = true;
            List<UserLast> usersUserLasts = userLastRepository.findAll();
            notifyObservers(getUserGameDto(usersUserLasts));
        }
    }

    private UserVoteDto getUserGameDto(List<UserLast> userLasts) {

        UserLast maxVotedUserLast = userLasts.stream()
                .max(Comparator.comparing(UserLast::getNumberVotes))
                .orElse(null);

        List<UserLast> maxVotedUserLasts = userLasts.stream()
                .filter(user -> Objects.equals(user.getNumberVotes(), maxVotedUserLast.getNumberVotes()))
                .toList();


        // Преобразуем выбранного пользователя в UserGameDto и сохраняем его в переменной result
        if (maxVotedUserLast != null && maxVotedUserLasts.size() == 1) {
            maxVotedUserLast.setDead(true);
            userLastRepository.save(maxVotedUserLast);

            Optional<GameState> gameStateOptional = gameStateRepository.findById(1L);
            if (gameStateOptional.isPresent()) {
                GameState gameState = gameStateOptional.get();
                if (gameState.getGameState() == 2) {

                    List<UserLast> userLastListNotDead = userLastRepository.findAll().stream()
                            .filter(user -> !user.isDead())
                            .toList();

//                List<GameCoordinates> gameCoordinatesList = gameCoordinatesRepository
//                        .findAll().stream()
//                        .filter(gameCoordinates -> !gameCoordinates.isCompleted())
//                        .toList();
                    long imposterCount = userLastListNotDead.stream().filter(UserLast::getIsImposter).count();
                    long notImposterCount = userLastListNotDead.size() - imposterCount;
                    // TODO : Нужно будет создать слушателя на GemaState
                    if (imposterCount > 0 && notImposterCount > 0) {
                        gameState.setGameState(1);

                        GameStateDto gameStateDto = GameStateMapper.toGameStateGto(gameStateRepository.save(gameState));
                        GameStateChangedEvent event = new GameStateChangedEvent(this, gameStateDto);
                        eventPublisher.publishEvent(event);
                    } else if ((imposterCount == 0 && notImposterCount > 0)) {
                        gameState.setGameState(3);
                        GameStateDto gameStateDto = GameStateMapper.toGameStateGto(gameStateRepository.save(gameState));
                        deleteAllUsers(gameState);
                        GameStateChangedEvent event = new GameStateChangedEvent(this, gameStateDto);
                        eventPublisher.publishEvent(event);
                    } else if ((imposterCount > 0 && notImposterCount == 0)) {
                        gameState.setGameState(4);
                        GameStateDto gameStateDto = GameStateMapper.toGameStateGto(gameStateRepository.save(gameState));
                        deleteAllUsers(gameState);
                        GameStateChangedEvent event = new GameStateChangedEvent(this, gameStateDto);
                        eventPublisher.publishEvent(event);
                    }
                }
            }


            return UserVoteMapper.toUserVoteDto(maxVotedUserLast);
        } else {
            Optional<GameState> gameStateOptional = gameStateRepository.findById(1L);
            if (gameStateOptional.isPresent()) {
                GameState gameState = gameStateOptional.get();
                if (gameState.getGameState() == 2) {
                    gameState.setGameState(1);
                    GameStateDto gameStateDto = GameStateMapper.toGameStateGto(gameStateRepository.save(gameState));
                    GameStateChangedEvent event = new GameStateChangedEvent(this, gameStateDto);
                    eventPublisher.publishEvent(event);

                }
            }
            return UserVoteDto.builder()
                    .id(-1L)
                    .login("null")
                    .build();
        }
    }

    // TODO : Нужно будет создать слушателя на GemaState
    private void deleteAllUsers(GameState gameState) {
        for (UserLast userLast : gameState.getUserLastList()) {
            userLastRepository.delete(userLast);
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

    @Override
    public void resetNumberVotes() {
        List<UserLast> userLastList = userLastRepository.findAll();
        for (UserLast userLast : userLastList) {
            userLast.setNumberVotes(0);
            userLastRepository.save(userLast);
        }
    }
}
