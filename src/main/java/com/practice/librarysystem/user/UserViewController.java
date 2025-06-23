package com.practice.librarysystem.user;

import com.practice.librarysystem.util.RequestConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserViewController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal, HttpServletRequest httpServletRequest) {
        String ip = RequestConstants.getClientIp(httpServletRequest);

        String email = principal.getName();

        model.addAttribute("user", userMapper.toDto(userService.findByEmail(email)));

        log.info("Endpoint GET: /profile was accessed by IP:{}", ip);

        return "profile";
    }

}

