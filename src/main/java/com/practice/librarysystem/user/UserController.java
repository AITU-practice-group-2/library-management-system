package com.practice.librarysystem.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public UserDto create(@RequestBody UserNewDto userNewDto) {
        return userMapper.toDto(userService.create(userNewDto));
    }

    @GetMapping
    public List<UserDto> findAll() {
        return userMapper.toDto(userService.findAll());
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable int id) {
        return userMapper.toDto(userService.findById(id));
    }

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable int id, @RequestBody UserNewDto newUser) {
        return userMapper.toDto(userService.update(id, newUser));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        userService.delete(id);
    }
}
