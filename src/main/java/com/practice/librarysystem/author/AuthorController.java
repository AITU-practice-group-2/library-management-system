package com.practice.librarysystem.author;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @PostMapping
    public AuthorDTO create(@RequestBody AuthorDTO dto) {
        return service.createAuthor(dto);
    }

    @GetMapping("/{id}")
    public AuthorDTO get(@PathVariable Long id) {
        return service.getAuthorById(id);
    }

    @GetMapping
    public List<AuthorDTO> getAll() {
        return service.getAllAuthors();
    }

    @PutMapping("/{id}")
    public AuthorDTO update(@PathVariable Long id, @RequestBody AuthorDTO dto) {
        return service.updateAuthor(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteAuthor(id);
    }
}
