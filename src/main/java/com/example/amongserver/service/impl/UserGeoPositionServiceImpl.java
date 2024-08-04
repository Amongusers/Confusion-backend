package com.example.amongserver.service.impl;

import com.example.amongserver.domain.entity.GameState;
import com.example.amongserver.domain.entity.User;
import com.example.amongserver.dto.GameStateDto;
import com.example.amongserver.dto.UserGeoPositionDto;
import com.example.amongserver.listener.GameStateChangedEvent;
import com.example.amongserver.mapper.GameStateMapper;
import com.example.amongserver.mapper.UserGameMapper;
import com.example.amongserver.mapper.UserGeoPositionMapper;
import com.example.amongserver.reposirory.GameStateRepository;
import com.example.amongserver.reposirory.UserRepository;
import com.example.amongserver.service.UserGeoPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserGeoPositionServiceImpl implements UserGeoPositionService {

    private final UserRepository userRepository;
    private final GameStateRepository gameStateRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public List<UserGeoPositionDto> updateGeoPosition(UserGeoPositionDto userGeoPositionDto) {
        Long id = userGeoPositionDto.getId();
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) throw new RuntimeException("User with ID " + id + " not found");

        User userDB = userOptional.get();
        User userClient = UserGeoPositionMapper.toUserEntity(userGeoPositionDto);
        if (!userDB.isDead()) {
            if (userClient.getLatitude() != null) userDB.setLatitude(userClient.getLatitude());
            if (userClient.getLongitude() != null) userDB.setLongitude(userClient.getLongitude());
        }

        // TODO: можно создать отдельный топик на передачу убитых игроков
        return userRepository.findAll()
                .stream()
                .map(UserGeoPositionMapper::toUserGeoPositionGto)
                .filter(e -> !e.isDead())
                .collect(Collectors.toList());


//        User userDB = userOptional.get();
//        User userClient = UserGeoPositionMapper.toUserEntity(userGeoPositionDto);

//        boolean isUserDeadClient = userClient.isDead();

//        if (userDB.isDead()) {
//            if (isUserDeadClient) {
//                Optional<GameState> gameStateOptional = gameStateRepository.findById(1L);
//                if (gameStateOptional.isPresent()) {
//                    GameState gameState = gameStateOptional.get();
//                    if ((gameState.getGameState() == 1 || gameState.getGameState() == 2) && notImposterCount == 0) {
//                        gameState.setGameState(4);
//                        GameStateDto gameStateDto = GameStateMapper.toGameStateGto(gameStateRepository.save(gameState));
//                        GameStateChangedEvent event = new GameStateChangedEvent(this, gameStateDto);
//                        eventPublisher.publishEvent(event);
//
//                    }
//                }
//            }
//            return null;
//        } else {
//
//            if (userClient.getLatitude() != null) userDB.setLatitude(userClient.getLatitude());
//            if (userClient.getLongitude() != null) userDB.setLongitude(userClient.getLongitude());
//           // userDB.setDead(userClient.isDead());
//            User userSave = userRepository.save(userDB);

//            List<User> userListNotDead = userRepository.findAll().stream()
//                    .filter(user -> !user.isDead())
//                    .toList();
//            long imposterCount = userListNotDead.stream().filter(User::getIsImposter).count();
//            long notImposterCount = userListNotDead.size() - imposterCount;
//
//
//            if (isUserDeadClient) {
//                Optional<GameState> gameStateOptional = gameStateRepository.findById(1L);
//                if (gameStateOptional.isPresent()) {
//                    GameState gameState = gameStateOptional.get();
//                    if ((gameState.getGameState() == 1 || gameState.getGameState() == 2) && notImposterCount == 0) {
//                        gameState.setGameState(4);
//                        deleteAllUsers(gameState);
//                        GameStateDto gameStateDto = GameStateMapper.toGameStateGto(gameStateRepository.save(gameState));
//                        GameStateChangedEvent event = new GameStateChangedEvent(this, gameStateDto);
//                        eventPublisher.publishEvent(event);
//
//                    }
//                }
//            }

//            return UserGeoPositionMapper.toUserGeoPositionGto(userSave);
//        }
//
    }


    // TODO : Нужно будет создать слушателя на GemaState
//    private void deleteAllUsers(GameState gameState) {
//        for (User user : gameState.getUserList()) {
//            userRepository.delete(user);
//        }
//    }
}