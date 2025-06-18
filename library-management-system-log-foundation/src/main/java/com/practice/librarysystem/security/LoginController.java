package com.practice.librarysystem.security;

import com.practice.librarysystem.user.UserNewDto;
import com.practice.librarysystem.user.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserNewDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("user") UserNewDto userDto, HttpServletRequest request) {
        userService.create(userDto);

        try {
            request.login(userDto.getEmail(), userDto.getPassword());
        } catch (ServletException e) {
            return "redirect:/login?error";
        }

        return "redirect:/";
    }
}
