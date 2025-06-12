package com.practice.librarysystem.author.controller;

import com.practice.librarysystem.author.dto.AuthorDTO;
import com.practice.librarysystem.author.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> create(@RequestBody AuthorDTO dto) {
        AuthorDTO createdAuthor = service.createAuthor(dto);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> get(@PathVariable Long id) {

        try{
            AuthorDTO author = service.getAuthorById(id);
            return new ResponseEntity<>(author, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAll() {
        List<AuthorDTO> authors = service.getAllAuthors();
        if(authors != null) {
            return new ResponseEntity<>(authors, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> update(@PathVariable Long id, @RequestBody AuthorDTO dto) {

        try{
            AuthorDTO updatedAuthor = service.updateAuthor(id, dto);
            return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deletedAuthor = service.deleteAuthor(id);
        if(deletedAuthor) {
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
