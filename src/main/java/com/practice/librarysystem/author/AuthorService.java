package com.practice.librarysystem.author;

import java.util.List;

public interface AuthorService {
    AuthorDTO createAuthor(AuthorDTO dto);
    AuthorDTO getAuthorById(Long id);
    List<AuthorDTO> getAllAuthors();
    AuthorDTO updateAuthor(Long id, AuthorDTO dto);
    void deleteAuthor(Long id);
}
