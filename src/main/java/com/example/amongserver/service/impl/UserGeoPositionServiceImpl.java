package com.example.amongserver.service.impl;

import com.example.amongserver.domain.entity.User;
import com.example.amongserver.dto.UserGeoPositionDto;
import com.example.amongserver.mapper.UserGeoPositionMapper;
import com.example.amongserver.reposirory.UserRepository;
import com.example.amongserver.service.UserGeoPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserGeoPositionServiceImpl implements UserGeoPositionService {

    private final UserRepository userRepository;

    @Override
    public UserGeoPositionDto updateGeoPosition(UserGeoPositionDto userGeoPositionDto) {
        Long id = userGeoPositionDto.getId();
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) throw new RuntimeException("User with ID " + id + " not found");

        User userDB = userOptional.get();
        User userClient = UserGeoPositionMapper.toUserEntity(userGeoPositionDto);

        if (userDB.isDead()) {
            return null;
        } else {
            if (userClient.getLatitude() != null) userDB.setLatitude(userClient.getLatitude());
            if (userClient.getLongitude() != null) userDB.setLongitude(userClient.getLongitude());
            userDB.setDead(userClient.isDead());
            return UserGeoPositionMapper.toUserGeoPositionGto(userRepository.save(userDB));
        }

    }
}
