package com.example.amongserver.room.mapper;

import com.example.amongserver.domain.Room;
import com.example.amongserver.domain.UserInGame;
import com.example.amongserver.room.controller.dto.CreateRoomRequestDto;
import com.example.amongserver.room.controller.dto.CreateRoomResponseDto;
import com.example.amongserver.room.controller.dto.UserInGameAdminResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.lang.Nullable;

import java.util.Set;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true),
        uses = {UserInGameMapper.class})
public interface RoomMapper {

    Room toRoomEntity(@Nullable final CreateRoomRequestDto createRoomRequestDto);

    @Mapping(target = "userInGame", source = "userInGameSet")
    CreateRoomResponseDto toCreateRoomResponseDto(@Nullable final Room room);

    // Внедрение UserInGameMapper
    UserInGameMapper userInGameMapper = Mappers.getMapper(UserInGameMapper.class); // Создание экземпляра маппера

    // Метод для маппинга одного пользователя из Set<UserInGame> в UserInGameAdminResponseDto
    default UserInGameAdminResponseDto mapSingleAdminUser(Set<UserInGame> users) {
        return users.stream()
                .findFirst() // Получаем первого найденного админа
                .map(userInGameMapper::toUserInGameAdminResponseDto) // Конвертируем его в DTO
                .orElse(null); // Возвращаем null, если админов нет
    }
}
