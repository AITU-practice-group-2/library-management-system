package com.practice.librarysystem.author.repository;

import com.practice.librarysystem.author.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
