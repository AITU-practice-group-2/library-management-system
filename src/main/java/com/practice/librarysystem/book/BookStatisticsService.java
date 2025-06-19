package com.practice.librarysystem.book;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

@Service
public class BookStatisticsService {
    BookRepository bookRepository;
    Set<String> uniqueViews;

    private static final int POPULARITY_FROM_VIEW = 1;

    public void addPopularityToBook(int popIndex, Book book) {
        book.setPopularity(book.getPopularity() + popIndex);

        bookRepository.save(book);
    }

    public void addPopularityToBook(int popIndex, Page<Book> books) {
        List<Book> modifiedBooks = books.stream()
                .peek(book -> book.setPopularity(book.getPopularity() + popIndex))
                .toList();

        bookRepository.saveAll(modifiedBooks);
    }

    public void addViewToBook(Book book, String ip) {
        String encoded = encode(ip, book.getId());

        if (!uniqueViews.contains(encoded)) {
            book.setViews(
                    book.getViews() + 1);

            book.setPopularity(book.getPopularity() + POPULARITY_FROM_VIEW);
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
                        book.setPopularity(book.getPopularity() + POPULARITY_FROM_VIEW);

                        uniqueViews.add(encoded);
                        return true;
                    }

                    return false;
                })
                .toList();

        bookRepository.saveAll(updatedEvents);
    }

    private String encode(String ip, Long bookId) {
        return String.format("ip:%s,event:%d", ip, bookId);
    }
}
