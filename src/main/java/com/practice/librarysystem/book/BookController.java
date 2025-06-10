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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createNew(@RequestBody Book book) {
        return bookMapper.toDto(
                bookService.createNew(book));
    }

    @PatchMapping("/{id}")
    public BookDto updateById(@RequestBody Book book,
                           @PathVariable int id) {
        return bookMapper.toDto(
                bookService.updateById(id, book));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        bookService.deleteById(id);
    }
}
