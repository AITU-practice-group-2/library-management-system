package com.practice.librarysystem.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Table(name = "users")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String login;
    String password;
    String email;
}
