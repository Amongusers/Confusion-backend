package com.example.amongserver.service.impl.impl;

import com.example.amongserver.domain.entity.GameState;
import com.example.amongserver.domain.entity.User;
import com.example.amongserver.dto.GameStateDto;
import com.example.amongserver.dto.UserGameDto;
import com.example.amongserver.dto.UserVoteDto;
import com.example.amongserver.listener.GameStateChangedEvent;
import com.example.amongserver.mapper.GameStateMapper;
import com.example.amongserver.mapper.UserGameMapper;
import com.example.amongserver.mapper.UserVoteMapper;
import com.example.amongserver.observer.UserVoteDtoObserver;
import com.example.amongserver.reposirory.GameStateRepository;
import com.example.amongserver.reposirory.UserRepository;
import com.example.amongserver.service.impl.UserVoteDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserVoteDtoServiceImpl implements UserVoteDtoService {
    private boolean isVoteCansel;

    private final UserRepository userRepository;
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
                        List<User> users = userRepository.findAll();
                        notifyObservers(getUserGameDto(users));
                    }
                }, 60000); // 1 минута


                gameState.setGameState(2);
                GameStateDto gameStateDto = GameStateMapper.toGameStateGto(gameStateRepository.save(gameState));
                GameStateChangedEvent event = new GameStateChangedEvent(this, gameStateDto);
                eventPublisher.publishEvent(event);
            }
        }
        return userRepository.findAllByIsDead(false)
                .stream()
                .map(UserGameMapper::toUserGameDto)
                .collect(Collectors.toList());
    }






    @Override
    public void vote(UserVoteDto userVoteDto) {
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

        if (totalVotes==users.size()) {
            timer.cancel();
            isVoteCansel = true;
            List<User> usersUsers = userRepository.findAll();
            notifyObservers(getUserGameDto(usersUsers));
        }
    }

    private UserVoteDto getUserGameDto(List<User> users) {

        User maxVotedUser = users.stream()
                .max(Comparator.comparing(User::getNumberVotes))
                .orElse(null);

        List<User> maxVotedUsers = users.stream()
                .filter(user -> Objects.equals(user.getNumberVotes(), maxVotedUser.getNumberVotes()))
                .toList();


        // Преобразуем выбранного пользователя в UserGameDto и сохраняем его в переменной result
        if (maxVotedUser != null && maxVotedUsers.size() == 1) {
            maxVotedUser.setDead(true);
            userRepository.save(maxVotedUser);

            Optional<GameState> gameStateOptional = gameStateRepository.findById(1L);
            if (gameStateOptional.isPresent()) {
                GameState gameState = gameStateOptional.get();
                if (gameState.getGameState() == 2) {

                    List<User> userListNotDead = userRepository.findAll().stream()
                            .filter(user -> !user.isDead())
                            .toList();

//                List<GameCoordinates> gameCoordinatesList = gameCoordinatesRepository
//                        .findAll().stream()
//                        .filter(gameCoordinates -> !gameCoordinates.isCompleted())
//                        .toList();
                    long imposterCount = userListNotDead.stream().filter(User::getIsImposter).count();
                    long notImposterCount = userListNotDead.size() - imposterCount;
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


            return UserVoteMapper.toUserVoteDto(maxVotedUser);
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
        for (User user : gameState.getUserList()) {
            userRepository.delete(user);
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
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            user.setNumberVotes(0);
            userRepository.save(user);
        }
    }
}
