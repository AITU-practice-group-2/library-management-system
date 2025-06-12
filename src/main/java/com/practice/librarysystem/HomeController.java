package com.practice.librarysystem;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String index(Model model) {
        model.addAttribute("message", "Welcome to the Library System");
        return "index"; // maps to templates/index.html
    }
}
