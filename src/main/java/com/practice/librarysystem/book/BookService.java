package com.practice.librarysystem.book;

import com.practice.librarysystem.author.Author;
import com.practice.librarysystem.author.AuthorRepository;
import com.practice.librarysystem.category.Category;
import com.practice.librarysystem.category.CategoryRepository;
import com.practice.librarysystem.exception.ForbiddenAccessException;
import com.practice.librarysystem.exception.NotFoundException;
import com.practice.librarysystem.statistics.user.UserAuthor;
import com.practice.librarysystem.statistics.user.UserAuthorRepository;
import com.practice.librarysystem.statistics.user.UserCategory;
import com.practice.librarysystem.statistics.user.UserCategoryRepository;
import com.practice.librarysystem.user.Role;
import com.practice.librarysystem.user.User;
import com.practice.librarysystem.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

@Service
public class BookService {
    BookRepository bookRepository;
    AuthorRepository authorRepository;
    CategoryRepository categoryRepository;
    UserRepository userRepository;

    UserCategoryRepository userCategoryRepository;
    UserAuthorRepository userAuthorRepository;

    public Page<Book> findAllByMultipleParams(String search, Long authorId, Long categoryId,
                                              int from, int size) {

        int pageNumber = from / size;
        Pageable pageable = PageRequest.of(pageNumber, size);

        if (search != null && !search.isBlank()
                && authorId != null && categoryId != null) {

            return bookRepository.findAllByTitleContainingAndAuthor_IdAndCategory_Id(
                    search, authorId, categoryId, pageable);
        }

        if (search != null && !search.isBlank()
                && authorId == null && categoryId == null) {

            return bookRepository.findAllByTitleContaining(search, pageable);
        }

        if (authorId != null
                && categoryId == null
                && (search == null || search.isBlank())) {

            return bookRepository.findAllByAuthor_Id(authorId, pageable);
        }

        if (categoryId != null
                && authorId == null
                && (search == null || search.isBlank())) {

            return bookRepository.findAllByCategory_Id(categoryId, pageable);
        }

        if (search != null && !search.isBlank()
                && authorId != null
                && categoryId == null) {

            return bookRepository.findAllByTitleContainingAndAuthor_Id(search, authorId, pageable);
        }

        if (search != null && !search.isBlank()
                && categoryId != null
                && authorId == null) {

            return bookRepository.findAllByTitleContainingAndCategory_Id(search, categoryId, pageable);
        }

        if (authorId != null
                && categoryId != null
                && (search == null || search.isBlank())) {

            return bookRepository.findAllByAuthor_IdAndCategory_Id(authorId, categoryId, pageable);
        }


        return bookRepository.findAll(pageable);
    }

    public Book findById(Long id) {
        return findByIdOrElseThrow(id);
    }

    public Book createNew(Book book, Long authorId, Long categoryId, String requesterEmail) {
        User user = findUserByEmailOrElseThrow(requesterEmail);

        if (user.getRole() != Role.ADMIN
                && user.getRole() != Role.EDITOR) {
            throw new ForbiddenAccessException(
                    String.format("Book creating failed for user %d", user.getId()));
        }

        Author author = findAuthorByIdOrElseThrow(authorId);

        Category category = findCategoryByIdOrElseThrow(categoryId);

        book.setAuthor(author);
        book.setCategory(category);

        bookRepository.save(book);


        return book;
    }

    public Book updateById(Long id, Book book, Long authorId, Long categoryId, String requesterEmail) {
        Book foundBook = findByIdOrElseThrow(id);

        User user = findUserByEmailOrElseThrow(requesterEmail);

        if (user.getRole() != Role.ADMIN
                && user.getRole() != Role.EDITOR) {
            throw new ForbiddenAccessException(
                    String.format("Book creating failed for user %d", user.getId()));
        }

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

        if (authorId != null) {
            Author author = findAuthorByIdOrElseThrow(authorId);
            foundBook.setAuthor(author);
        }

        if (book.getCategory() != null) {
            Category category = findCategoryByIdOrElseThrow(categoryId);
            foundBook.setCategory(category);
        }

        bookRepository.save(foundBook);


        return foundBook;
    }

    public void deleteById(Long id, String requesterEmail) {
        findByIdOrElseThrow(id);

        User user = findUserByEmailOrElseThrow(requesterEmail);

        if (user.getRole() != Role.ADMIN
                && user.getRole() != Role.EDITOR) {
            throw new ForbiddenAccessException(
                    String.format("Book creating failed for user %d", user.getId()));
        }

        bookRepository.deleteById(id);
    }

    public List<Book> findAllRecommended(String email) {
        User currentUser = findUserByEmailOrElseThrow(email);

        UserCategory userCategory = userCategoryRepository.findFirstByUserIdOrderByPopularity(currentUser.getId())
                .orElseThrow(() -> new NotFoundException("Statistics error"));

        UserAuthor userAuthor = userAuthorRepository.findFirstByUserIdOrderByPopularity(currentUser.getId())
                .orElseThrow(() -> new NotFoundException("Statistics error"));

        return bookRepository.findTop3ByAuthor_IdAndAvailableGreaterThanOrCategory_IdAndAvailableGreaterThan(
                userAuthor.getAuthor().getId(), 0, userCategory.getCategory().getId(), 0);
    }

    public Page<Book> findAllPopular(int from, int size) {
        int pageNum = from / size;
        Pageable pageable = PageRequest.of(pageNum, size);

        return bookRepository.findAllByAvailableGreaterThanOrderByPopularity(0, pageable);
    }

    private Book findByIdOrElseThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Book with id: %d not found", id)));
    }

    private Author findAuthorByIdOrElseThrow(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Author with id: %d not found", authorId)));
    }

    private Category findCategoryByIdOrElseThrow(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Category with id: %d not found", categoryId)));
    }

    private User findUserByEmailOrElseThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with email: %s not found", email)));
    }
}
