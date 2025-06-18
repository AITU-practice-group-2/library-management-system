package com.practice.librarysystem.book;

import com.practice.librarysystem.book.dto.BookFullResponse;
import com.practice.librarysystem.book.dto.BookShortResponse;
import com.practice.librarysystem.book.dto.NewBookRequest;
import com.practice.librarysystem.book.dto.UpdateBookRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {
    BookService bookService;
    BookMapper bookMapper;

    @GetMapping
    public List<BookShortResponse> findAllByMultipleParams(@RequestParam(required = false) String search,
                                                           @RequestParam(required = false) @Min(1) Long author,
                                                           @RequestParam(required = false) @Min(1) Long category,
                                                           @RequestParam(defaultValue = "0") int from,
                                                           @RequestParam(defaultValue = "10") int size) {
        return bookMapper.toShortDto(
                bookService.findAllByMultipleParams(search, author, category, from, size));
    }

    @GetMapping("/{id}")
    public BookFullResponse findById(@PathVariable Long id) {
        return bookMapper.toDto(
                bookService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookFullResponse createNew(@RequestBody @Valid NewBookRequest bookDto) {
        Long authorId = bookDto.getAuthor();
        Long categoryId = bookDto.getCategory();

        Book book = bookMapper.fromDto(bookDto);

        return bookMapper.toDto(
                bookService.createNew(book, authorId, categoryId));
    }

    @PatchMapping("/{id}")
    public BookFullResponse updateById(@PathVariable Long id,
                                       @RequestBody @Valid UpdateBookRequest bookDto) {
        Long authorId = bookDto.getAuthor();
        Long categoryId = bookDto.getCategory();

        Book book = bookMapper.fromDto(bookDto);

        return bookMapper.toDto(
                bookService.updateById(id, book, authorId, categoryId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
