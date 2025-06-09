package com.practice.librarysystem.book;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookMapper {
    public BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();

        bookDto.setAuthor(bookDto.getAuthor());
        bookDto.setCategory(bookDto.getCategory());
        bookDto.setAvailable(book.getAvailable());
        bookDto.setPages(book.getPages());
        bookDto.setPublicationYear(book.getPublicationYear());
        bookDto.setTitle(book.getTitle());
        bookDto.setDescription(book.getDescription());

        return bookDto;
    }

    public List<BookDto> toDto(Page<Book> books) {
        return books.stream()
                .map(this::toDto)
                .toList();
    }

    public Book fromDto(BookDto dto) {
        Book book = new Book();

        book.setAvailable(dto.getAvailable());
        book.setPages(dto.getPages());
        book.setDescription(dto.getDescription());
        book.setTitle(dto.getTitle());
        book.setPublicationYear(dto.getPublicationYear());
        book.setAuthorId(dto.getAuthor());
        book.setCategoryId(dto.getCategory());

        return book;
    }
}
