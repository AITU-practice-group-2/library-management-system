package com.practice.librarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.librarysystem.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
