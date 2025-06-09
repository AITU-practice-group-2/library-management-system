package com.practice.librarysystem.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@RequiredArgsConstructor
public class UserDto {
    int id;
    String login;
    String password;
    String email;
}
