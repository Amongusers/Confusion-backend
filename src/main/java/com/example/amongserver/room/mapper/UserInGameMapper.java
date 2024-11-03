package com.example.amongserver.room.mapper;

import com.example.amongserver.domain.UserInGame;
import com.example.amongserver.room.controller.dto.UserInGameAdminResponseDto;
import com.example.amongserver.room.controller.dto.UserInGameResponseDto;
import com.sun.istack.Nullable;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface UserInGameMapper {

    UserInGameAdminResponseDto toUserInGameAdminResponseDto(@Nullable final UserInGame userInGame);

    UserInGameResponseDto toUserInGameResponseDto(@Nullable final UserInGame userInGame);
}
