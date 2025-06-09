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
public class UserNewDto {
    @Size(min = 5, max = 100, message = "Login has to contain 5-100 words.")
    @NotBlank(message = "Login cannot be empty.")
    String login;
    @Size(min = 5, max = 50, message = "Password has to contain 5-50 words.")
    @NotBlank(message = "Password cannot be empty.")
    String password;
    @Size(min = 5, max = 100, message = "Email has to contain 5-100 words.")
    @NotBlank(message = "Email cannot be empty.")
    @Email(message = "Not correct format.")
    String email;
}
