package com.practice.librarysystem.author.service;

import com.practice.librarysystem.author.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    AuthorDTO createAuthor(AuthorDTO dto);
    AuthorDTO getAuthorById(Long id);
    List<AuthorDTO> getAllAuthors();
    AuthorDTO updateAuthor(Long id, AuthorDTO dto);
    boolean deleteAuthor(Long id);
}
