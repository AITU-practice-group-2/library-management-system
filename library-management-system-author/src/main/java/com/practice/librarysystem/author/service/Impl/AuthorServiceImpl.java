package com.practice.librarysystem.author.service.Impl;

import com.practice.librarysystem.author.dto.AuthorDTO;
import com.practice.librarysystem.author.entity.Author;
import com.practice.librarysystem.author.mapper.AuthorMapper;
import com.practice.librarysystem.author.repository.AuthorRepository;
import com.practice.librarysystem.author.service.AuthorService;
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
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return AuthorMapper.toDTO(author);
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return repository.findAll().stream()
                .map(AuthorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO dto) {
        Author existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        existing.setName(dto.getName());
        existing.setBio(dto.getBio());

        return AuthorMapper.toDTO(repository.save(existing));
    }

    @Override
    public boolean deleteAuthor(Long id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }
}
