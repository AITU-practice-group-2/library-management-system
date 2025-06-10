package com.practice.librarysystem.book;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

@Service
public class BookService {
    BookRepository bookRepository;

    public Page<Book> findAll(int from, int size) {
        int pageNumber = from / size;
        Pageable pageable = PageRequest.of(pageNumber, size);

        return bookRepository.findAll(pageable);
    }

    public Book findById(long id) {
        return bookRepository.findById(id)
                .orElseThrow();
    }
}
