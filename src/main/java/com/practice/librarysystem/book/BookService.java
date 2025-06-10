package com.practice.librarysystem.book;

import com.practice.librarysystem.exception.NotFoundException;
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

    public Book findById(int id) {
        return findByIdOrElseThrow(id);
    }

    public Book createNew(Book book) {
        bookRepository.save(book);

        return book;
    }

    public Book updateById(int id, Book book) {
        Book foundBook = findByIdOrElseThrow(id);

        if (book.getTitle() != null) {
            foundBook.setTitle(book.getTitle());
        }

        if (book.getDescription() != null) {
            foundBook.setDescription(book.getDescription());
        }

        if (book.getPublicationYear() != null) {
            foundBook.setPublicationYear(book.getPublicationYear());
        }

        if (book.getPages() != null) {
            foundBook.setPages(book.getPages());
        }

        if (book.getAvailable() != null) {
            foundBook.setAvailable(book.getAvailable());
        }

        if (book.getAuthor() != null) {
            foundBook.setAuthor(book.getAuthor());
        }

        if (book.getCategory() != null) {
            foundBook.setCategory(book.getCategory());
        }

        bookRepository.save(foundBook);


        return foundBook;
    }

    public void deleteById(int id) {
        findByIdOrElseThrow(id);

        bookRepository.deleteById(id);
    }

    private Book findByIdOrElseThrow(int id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Book with id %d not found", id)));
    }
}
