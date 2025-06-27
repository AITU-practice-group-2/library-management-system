package com.practice.librarysystem.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserUpdateDto {
    @Size(min = 5, max = 100, message = "Login has to contain 5-100 words.")
    String login;
    @Size(min = 5, max = 255, message = "Password has to contain 5-50 words.")
    String password;
    @Size(min = 5, max = 100, message = "Email has to contain 5-100 words.")
    @Email(message = "Not correct format.")
    String email;
    String role;
}
