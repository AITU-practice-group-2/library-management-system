package com.practice.librarysystem.user;

import com.practice.librarysystem.reservation.Reservation;
import com.practice.librarysystem.review.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String login;
    String password;
    String email;
    @Enumerated(value = EnumType.STRING)
    Role role;
    @OneToMany(mappedBy = "user")
    List<Review> reviews = new ArrayList<>();
    @OneToMany(mappedBy = "reserver")
    List<Reservation> reservations = new ArrayList<>();
}
