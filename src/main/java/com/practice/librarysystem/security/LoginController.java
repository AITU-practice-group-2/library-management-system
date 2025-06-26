package com.practice.librarysystem.security;

import com.practice.librarysystem.user.UserController;
import com.practice.librarysystem.user.UserNewDto;
import com.practice.librarysystem.util.RequestConstants;
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
        String ip = RequestConstants.getClientIp(httpServletRequest);
        if (error != null) {
            log.warn("Authorisation failed from IP: {}", ip);
        }
        log.info("Endpoint GET: /login was accessed by IP:{}", ip);
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("user", new UserNewDto());
        String ip = RequestConstants.getClientIp(httpServletRequest);
        log.info("Endpoint GET: /register was accessed by IP:{}", ip);
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("user") UserNewDto userDto, HttpServletRequest request) {
        String ip = RequestConstants.getClientIp(request);
        try {
            log.info("Successful authorisation from IP:{}!", ip);
            request.login(userDto.getEmail(), userDto.getPassword());
        } catch (ServletException e) {
            log.warn("Authorisation failed from IP:{}.", ip);
            return "redirect:/login?error";
        }

        userController.create(userDto, request);
        return "redirect:/";
    }
}
