package com.practice.librarysystem.author;

import com.practice.librarysystem.util.RequestConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO create(@RequestBody AuthorDTO dto, HttpServletRequest request) {
        log.info("Endpoint POST: /authors was accessed by IP: {}", RequestConstants.getClientIp(request));
        return service.createAuthor(dto);
    }

    @GetMapping("/{id}")
    public AuthorDTO get(@PathVariable Long id, HttpServletRequest request) {
        log.info("Endpoint GET: /authors/{} was accessed by IP: {}", id, RequestConstants.getClientIp(request));
        return service.getAuthorById(id);
    }

    @GetMapping
    public List<AuthorDTO> getAll(@RequestParam(defaultValue = "0") int from,
                                  @RequestParam(defaultValue = "10") int size,
                                  HttpServletRequest request) {
        log.info("Endpoint GET: /authors was accessed by IP: {}", RequestConstants.getClientIp(request));
        return service.getAllAuthors(from, size);
    }

    @PutMapping("/{id}")
    public AuthorDTO update(@PathVariable Long id, @RequestBody AuthorDTO dto, HttpServletRequest request) {
        log.info("Endpoint PUT: /authors/{} was accessed by IP: {}", id, RequestConstants.getClientIp(request));
        return service.updateAuthor(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, HttpServletRequest request) {
        log.info("Endpoint DELETE: /authors/{} was accessed by IP: {}", id, RequestConstants.getClientIp(request));
        service.deleteAuthor(id);
    }
}
