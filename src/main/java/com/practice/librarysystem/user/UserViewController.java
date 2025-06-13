package com.practice.librarysystem.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserViewController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        String email = principal.getName(); // this is the email, since login is based on email

        User user = userService.findByEmail(email);

        UserDto userDto = new UserDto();
        userDto.setLogin(user.getLogin());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword()); // only if you're showing raw passwords (NOT recommended)
        userDto.setRole(user.getRole().toString());

        model.addAttribute("user", userDto);
        return "profile";
    }

}

