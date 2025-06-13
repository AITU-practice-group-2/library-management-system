package com.practice.librarysystem.book;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/library")
public class BookViewController {
    @GetMapping
    public String showBookListPage() {
        return "book-list";
    }

    @GetMapping("/{id}")
    public String showBookDetailsPage() {
        return "book-details";
    }
}
