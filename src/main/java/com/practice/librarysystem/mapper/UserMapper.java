package com.practice.librarysystem.mapper;

import org.mapstruct.Mapper;

import com.practice.librarysystem.dto.UserDto;
import com.practice.librarysystem.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    List<UserDto> toDto(List<User> user);
}
