package com.practice.librarysystem.book;

import com.practice.librarysystem.book.dto.BookFullResponse;
import com.practice.librarysystem.book.dto.BookShortResponse;
import com.practice.librarysystem.book.dto.NewBookRequest;
import com.practice.librarysystem.book.dto.UpdateBookRequest;
import com.practice.librarysystem.statistics.book.BookStatisticsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.practice.librarysystem.util.RequestConstants.getClientIp;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {
    BookService bookService;
    BookMapper bookMapper;

    BookStatisticsService statisticsService;

    @GetMapping
    public List<BookShortResponse> findAllByMultipleParams(@RequestParam(required = false) String search,
                                                           @RequestParam(required = false) @Min(1) Long author,
                                                           @RequestParam(required = false) @Min(1) Long category,
                                                           @RequestParam(defaultValue = "0") int from,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           HttpServletRequest httpServletRequest) {
        String ip = getClientIp(httpServletRequest);
        log.info("Endpoint GET: /books was accessed by IP:{}", ip);

        Page<Book> result = bookService.findAllByMultipleParams(search, author, category, from, size);

        statisticsService.addViewToBook(result, ip);

        return bookMapper.toShortDto(result);
    }

    @GetMapping("/{id}")
    public BookFullResponse findById(@PathVariable Long id,
                                     HttpServletRequest httpServletRequest,
                                     Principal principal) {
        String ip = getClientIp(httpServletRequest);
        log.info("Endpoint GET: /books/{} was accessed by IP:{}", id, ip);

        Book result = bookService.findById(id);
        String email = principal.getName();

        statisticsService.addViewToBook(result, ip, email);

        return bookMapper.toDto(result);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookFullResponse createNew(@RequestBody @Valid NewBookRequest bookDto,
                                      HttpServletRequest httpServletRequest,
                                      Principal principal) {

        log.info("Endpoint POST: /books was accessed by IP:{}", getClientIp(httpServletRequest));

        Long authorId = bookDto.getAuthor();
        Long categoryId = bookDto.getCategory();
        String email = principal.getName();

        Book book = bookMapper.fromDto(bookDto);

        return bookMapper.toDto(
                bookService.createNew(book, authorId, categoryId, email));
    }

    @PatchMapping("/{id}")
    public BookFullResponse updateById(@PathVariable Long id,
                                       @RequestBody @Valid UpdateBookRequest bookDto,
                                       HttpServletRequest httpServletRequest,
                                       Principal principal) {

        log.info("Endpoint PATCH: /books/{id} was accessed by IP:{}", getClientIp(httpServletRequest));

        Long authorId = bookDto.getAuthor();
        Long categoryId = bookDto.getCategory();
        String email = principal.getName();

        Book book = bookMapper.fromDto(bookDto);

        return bookMapper.toDto(
                bookService.updateById(id, book, authorId, categoryId, email));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id,
                           HttpServletRequest httpServletRequest,
                           Principal principal) {

        log.info("Endpoint DELETE: /books/{id} was accessed by IP:{}", getClientIp(httpServletRequest));

        String email = principal.getName();

        bookService.deleteById(id, email);
    }

    @GetMapping("/recommendations")
    public List<BookShortResponse> findAllRecommended(HttpServletRequest httpServletRequest,
                                                      Principal principal) {

        log.info("Endpoint GET: /books/recommendations was accessed by IP:{}", getClientIp(httpServletRequest));

        if (principal == null) {
            return List.of();
        }

        String email = principal.getName();

        return bookMapper.toShortDto(
                bookService.findAllRecommended(email));
    }

    @GetMapping("/popular")
    public List<BookShortResponse> findAllPopular(@RequestParam(defaultValue = "0") int from,
                                                  @RequestParam(defaultValue = "3") int size,
                                                  HttpServletRequest httpServletRequest) {

        log.info("Endpoint GET: /books/popular was accessed by IP:{}", getClientIp(httpServletRequest));

        return bookMapper.toShortDto(
                bookService.findAllPopular(from, size));
    }
}
