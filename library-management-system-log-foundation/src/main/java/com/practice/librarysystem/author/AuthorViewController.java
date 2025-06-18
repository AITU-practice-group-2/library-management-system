package com.practice.librarysystem.author;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.practice.librarysystem.util.RequestConstants.getClientIp;

@Controller
@RequestMapping("/authors-view")
@Slf4j
public class AuthorViewController {
    
    @GetMapping
    public String showAuthorListPage(HttpServletRequest httpServletRequest) {
        log.info("Endpoint GET: /authors-view was accessed by IP:{}", getClientIp(httpServletRequest));
        return "author-list";
    }

    @GetMapping("/{id}")
    public String showAuthorDetailsPage(@PathVariable Long id,
                                      HttpServletRequest httpServletRequest) {
        log.info("Endpoint GET: /authors-view/{} was accessed by IP:{}", id, getClientIp(httpServletRequest));
        return "author-details";
    }
} 