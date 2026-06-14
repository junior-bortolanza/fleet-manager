package com.bortolanza.fleet.modules.user.dto.mapper;

import com.bortolanza.fleet.modules.user.dto.in.UserRequestDTO;
import com.bortolanza.fleet.modules.user.dto.out.UserResponseDTO;
import com.bortolanza.fleet.modules.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDTO dto);
    UserResponseDTO toResponseDTO(User user);
    User updateEntity(UserRequestDTO dto, User user);
}
