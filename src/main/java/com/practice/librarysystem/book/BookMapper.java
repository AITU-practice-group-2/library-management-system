package com.practice.librarysystem.book;

import com.practice.librarysystem.book.dto.BookResponse;
import com.practice.librarysystem.book.dto.NewBookRequest;
import com.practice.librarysystem.book.dto.UpdateBookRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookMapper {
    public BookResponse toDto(Book book) {
        BookResponse bookDto = new BookResponse();

        bookDto.setId(book.getId());
        bookDto.setAuthor(book.getAuthor().getName());
        bookDto.setCategory(book.getCategory().getName());
        bookDto.setAvailable(book.getAvailable());
        bookDto.setPages(book.getPages());
        bookDto.setPublicationYear(book.getPublicationYear());
        bookDto.setTitle(book.getTitle());
        bookDto.setDescription(book.getDescription());

        return bookDto;
    }

    public List<BookResponse> toDto(Page<Book> books) {
        return books.stream()
                .map(this::toDto)
                .toList();
    }

    public Book fromDto(NewBookRequest dto) {
        Book book = new Book();

        book.setAvailable(dto.getAvailable());
        book.setPages(dto.getPages());
        book.setDescription(dto.getDescription());
        book.setTitle(dto.getTitle());
        book.setPublicationYear(dto.getPublicationYear());


        return book;
    }

    public Book fromDto(UpdateBookRequest dto) {
        Book book = new Book();

        book.setAvailable(dto.getAvailable());
        book.setPages(dto.getPages());
        book.setDescription(dto.getDescription());
        book.setTitle(dto.getTitle());
        book.setPublicationYear(dto.getPublicationYear());


        return book;
    }
}
