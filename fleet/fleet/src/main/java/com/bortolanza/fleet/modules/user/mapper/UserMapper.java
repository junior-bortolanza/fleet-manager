package com.bortolanza.fleet.modules.user.mapper;

import com.bortolanza.fleet.modules.user.dto.in.UserRequestDTO;
import com.bortolanza.fleet.modules.user.dto.out.UserResponseDTO;
import com.bortolanza.fleet.modules.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDTO dto);

    UserResponseDTO toResponseDTO(User user);

    void updateEntity(UserRequestDTO dto, @MappingTarget User user);
}