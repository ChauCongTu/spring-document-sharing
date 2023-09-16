package com.nhom_4.backend.mappers;

import com.nhom_4.backend.dtos.SignUpDto;
import com.nhom_4.backend.entites.User;
import com.nhom_4.backend.dtos.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

}
