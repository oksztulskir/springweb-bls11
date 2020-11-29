package com.sda.mappers;

import com.sda.dto.CreateOrUpdateUserDto;
import com.sda.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 */
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(CreateOrUpdateUserDto dto);

    CreateOrUpdateUserDto toDto(User entity);
}
