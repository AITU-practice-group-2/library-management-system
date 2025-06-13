package com.practice.librarysystem.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // This renders login.html with CSRF token auto-injected
    }


    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserNewDto());
        return "register"; // Thymeleaf template
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerSubmit(@ModelAttribute("user") UserNewDto userDto, HttpServletRequest request) {
        userService.create(userDto); // Save user to DB

        try {
            request.login(userDto.getLogin(), userDto.getPassword()); // Auto login
        } catch (ServletException e) {
            return "redirect:/login?error";
        }

        return "redirect:/"; // Redirect to homepage
    }
}

