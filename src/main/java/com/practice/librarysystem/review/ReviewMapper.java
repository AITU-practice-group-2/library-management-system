package com.practice.librarysystem.review;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "book.title", target = "bookTitle")
    @Mapping(source = "user.login", target = "userName")
    ReviewResponseDTO toDTO(Review review);
}
