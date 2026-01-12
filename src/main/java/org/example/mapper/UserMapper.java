package org.example.mapper;

import org.example.dto.user.UserResponse;
import org.example.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toDto(User user);
}
