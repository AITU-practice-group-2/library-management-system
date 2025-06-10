package com.practice.librarysystem.book;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

@RestController
@RequestMapping("/books")
public class BookController {
    BookService bookService;
    BookMapper bookMapper;

    @GetMapping
    public List<BookDto> findAll(@RequestParam(defaultValue = "0") int from,
                              @RequestParam(defaultValue = "10") int size) {
        return bookMapper.toDto(
                bookService.findAll(from, size));
    }

    @GetMapping("/{id}")
    public BookDto findById(@PathVariable int id) {
        return bookMapper.toDto(
                bookService.findById(id));
    }

    //TODO: create, update
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createNew(@RequestBody Book book,
                          @RequestHeader int userId) {
        return null;
    }

    @PatchMapping("/{id}")
    public Book updateById(@RequestBody Book book,
                           @PathVariable int id) {
        return null;
    }
}
