package com.practice.librarysystem.author.mapper;

import com.practice.librarysystem.author.dto.AuthorDTO;
import com.practice.librarysystem.author.entity.Author;

public class AuthorMapper {

    public static AuthorDTO toDTO(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .bio(author.getBio())
                .build();
    }

    public static Author toEntity(AuthorDTO dto) {
        return Author.builder()
                .name(dto.getName())
                .bio(dto.getBio())
                .build();
    }
}
