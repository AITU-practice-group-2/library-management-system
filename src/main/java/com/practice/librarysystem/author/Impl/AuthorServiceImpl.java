package com.practice.librarysystem.author.Impl;

import com.practice.librarysystem.author.AuthorDTO;
import com.practice.librarysystem.author.Author;
import com.practice.librarysystem.author.AuthorMapper;
import com.practice.librarysystem.author.AuthorRepository;
import com.practice.librarysystem.author.AuthorService;
import com.practice.librarysystem.exception.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
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
    public List<AuthorDTO> getAllAuthors(int from, int size) {
        int pageNumber = from / size;

        Pageable pageable = PageRequest.of(pageNumber, size);

        return repository.findAll(pageable).stream()
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
