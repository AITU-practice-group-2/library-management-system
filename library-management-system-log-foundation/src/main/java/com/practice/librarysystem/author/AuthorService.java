package com.practice.librarysystem.author;

import com.practice.librarysystem.author.dto.AuthorWithBooksRequest;
import com.practice.librarysystem.author.AuthorWithBooksDTO;

import java.util.List;

public interface AuthorService {
    AuthorDTO createAuthor(AuthorDTO dto);
    AuthorDTO getAuthorById(Long id);
    AuthorWithBooksDTO getAuthorWithBooksById(Long id);
    List<AuthorDTO> getAllAuthors(int from, int size);
    List<AuthorDTO> searchAuthors(String search, int from, int size);
    AuthorDTO updateAuthor(Long id, AuthorDTO dto);
    void deleteAuthor(Long id);
    AuthorWithBooksDTO createAuthorWithBooks(AuthorWithBooksRequest request);
}
