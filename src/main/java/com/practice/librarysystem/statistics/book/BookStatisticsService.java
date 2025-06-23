package com.practice.librarysystem.statistics.book;

import com.practice.librarysystem.book.Book;
import com.practice.librarysystem.book.BookRepository;
import com.practice.librarysystem.exception.NotFoundException;
import com.practice.librarysystem.statistics.user.PopularityService;
import com.practice.librarysystem.user.User;
import com.practice.librarysystem.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

@Service
public class BookStatisticsService {
    BookRepository bookRepository;
    Set<String> uniqueViews;

    PopularityService popularityService;

    UserRepository userRepository;

    private static final int POPULARITY_FROM_VIEW = 1;

    public void addPopularityToBook(int popIndex, Book book, String email) {
        book.setPopularity(book.getPopularity() + popIndex);

        User currentUser = findUserByEmailOrElseThrow(email);

        popularityService.addAuthorPopularity(popIndex, currentUser, book.getAuthor());
        popularityService.addCategoryPopularity(popIndex, currentUser, book.getCategory());

        bookRepository.save(book);
    }

    public void addViewToBook(Book book, String ip, String email) {
        String encoded = encode(ip, book.getId());

        User currentUser = findUserByEmailOrElseThrow(email);

        if (!uniqueViews.contains(encoded)) {
            book.setViews(
                    book.getViews() + 1);

            book.setPopularity(book.getPopularity() + POPULARITY_FROM_VIEW);
            popularityService.addAuthorPopularity(POPULARITY_FROM_VIEW, currentUser, book.getAuthor());
            popularityService.addCategoryPopularity(POPULARITY_FROM_VIEW, currentUser, book.getCategory());

            bookRepository.save(book);
            uniqueViews.add(encoded);
        }
    }

    public void addViewToBook(Page<Book> books, String ip) {
        List<Book> updatedEvents = books.stream()
                .filter(book -> {
                    String encoded = encode(ip, book.getId());

                    if (!uniqueViews.contains(encoded)) {
                        book.setViews(book.getViews() + 1);

                        uniqueViews.add(encoded);
                        return true;
                    }

                    return false;
                })
                .toList();

        bookRepository.saveAll(updatedEvents);
    }

    private String encode(String ip, long bookId) {
        return String.format("ip:%s,event:%d", ip, bookId);
    }

    private User findUserByEmailOrElseThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with email: %s not found", email)));
    }
}
