package com.practice.librarysystem.security;

import com.practice.librarysystem.user.UserController;
import com.practice.librarysystem.user.UserNewDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final UserController userController;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            HttpServletRequest httpServletRequest) {
        String ip = httpServletRequest.getRemoteAddr();
        if (error != null) {
            log.warn("Authorisation failed from IP: {}", ip);
        }
        log.info("Endpoint GET: /login was accessed by IP:{}", ip);
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("user", new UserNewDto());
        String ip = httpServletRequest.getRemoteAddr();
        log.info("Endpoint GET: /register was accessed by IP:{}", ip);
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("user") UserNewDto userDto, HttpServletRequest request) {
        userController.create(userDto, request);

        try {
            log.info("Successful authorisation!");
            request.login(userDto.getEmail(), userDto.getPassword());
        } catch (ServletException e) {
            log.warn("Authorisation failed.");
            return "redirect:/login?error";
        }

        return "redirect:/";
    }
}
