package com.practice.librarysystem.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    @Mapping(source = "login", target = "login")
    List<UserDto> toDto(List<User> user);
}
