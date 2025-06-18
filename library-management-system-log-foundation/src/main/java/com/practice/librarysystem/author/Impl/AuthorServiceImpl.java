package com.practice.librarysystem.author.Impl;

import com.practice.librarysystem.author.AuthorDTO;
import com.practice.librarysystem.author.Author;
import com.practice.librarysystem.author.AuthorMapper;
import com.practice.librarysystem.author.AuthorRepository;
import com.practice.librarysystem.author.AuthorService;
import com.practice.librarysystem.author.AuthorWithBooksDTO;
import com.practice.librarysystem.author.dto.AuthorWithBooksRequest;
import com.practice.librarysystem.book.Book;
import com.practice.librarysystem.book.BookMapper;
import com.practice.librarysystem.book.BookService;
import com.practice.librarysystem.book.dto.BookShortResponse;
import com.practice.librarysystem.category.Category;
import com.practice.librarysystem.category.CategoryRepository;
import com.practice.librarysystem.exception.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;
    private final BookMapper bookMapper;
    private final BookService bookService;
    private final CategoryRepository categoryRepository;

    public AuthorServiceImpl(AuthorRepository repository, BookMapper bookMapper, BookService bookService, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.bookMapper = bookMapper;
        this.bookService = bookService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO dto) {
        Author saved = repository.save(AuthorMapper.toEntity(dto));
        return AuthorMapper.toDTO(saved);
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found"));
        return AuthorMapper.toDTO(author);
    }

    @Override
    public AuthorWithBooksDTO getAuthorWithBooksById(Long id) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found"));
        
        List<BookShortResponse> books = author.getBooks().stream()
                .map(bookMapper::toShortDto)
                .collect(Collectors.toList());
        
        return AuthorWithBooksDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .bio(author.getBio())
                .books(books)
                .build();
    }

    @Override
    @Transactional
    public AuthorWithBooksDTO createAuthorWithBooks(AuthorWithBooksRequest request) {
        // Create the author first
        Author author = new Author();
        author.setName(request.getName());
        author.setBio(request.getBio());
        Author savedAuthor = repository.save(author);
        
        // Create books for this author
        List<BookShortResponse> createdBooks = request.getBooks().stream()
                .map(bookRequest -> {
                    Book book = bookMapper.fromDto(bookRequest);
                    book.setAuthor(savedAuthor);
                    
                    // Set category if provided
                    if (bookRequest.getCategory() != null) {
                        Category category = categoryRepository.findById(bookRequest.getCategory())
                                .orElseThrow(() -> new NotFoundException("Category not found"));
                        book.setCategory(category);
                    }
                    
                    Book savedBook = bookService.createNew(book, savedAuthor.getId(), bookRequest.getCategory());
                    return bookMapper.toShortDto(savedBook);
                })
                .collect(Collectors.toList());
        
        return AuthorWithBooksDTO.builder()
                .id(savedAuthor.getId())
                .name(savedAuthor.getName())
                .bio(savedAuthor.getBio())
                .books(createdBooks)
                .build();
    }

    @Override
    public List<AuthorDTO> getAllAuthors(int from, int size) {
        int pageNumber = from / size;

        Pageable pageable = PageRequest.of(pageNumber, size);

        return repository.findAll(pageable).stream()
                .map(AuthorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuthorDTO> searchAuthors(String search, int from, int size) {
        int pageNumber = from / size;
        Pageable pageable = PageRequest.of(pageNumber, size);
        
        if (search == null || search.trim().isEmpty()) {
            return getAllAuthors(from, size);
        }
        
        return repository.findByNameContainingIgnoreCase(search.trim(), pageable).stream()
                .map(AuthorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO dto) {
        Author existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found"));

        existing.setName(dto.getName());
        existing.setBio(dto.getBio());

        return AuthorMapper.toDTO(repository.save(existing));
    }

    @Override
    public void deleteAuthor(Long id) {
        repository.deleteById(id);
    }
}
