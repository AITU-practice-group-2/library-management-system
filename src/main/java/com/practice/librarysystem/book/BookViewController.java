package com.practice.librarysystem.book;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.practice.librarysystem.util.RequestConstants.getClientIp;

@Controller
@RequestMapping("/library")
@Slf4j
public class BookViewController {
    @GetMapping
    public String showBookListPage(HttpServletRequest httpServletRequest) {
        log.info("Endpoint GET: /library was accessed by IP:{}", getClientIp(httpServletRequest));

        return "book-list";
    }

    @GetMapping("/{id}")
    public String showBookDetailsPage(@PathVariable Long id,
                                      HttpServletRequest httpServletRequest) {
        log.info("Endpoint GET: /library/{} was accessed by IP:{}", id, getClientIp(httpServletRequest));

        return "book-details";
    }
}
