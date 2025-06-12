package com.practice.librarysystem.user;

import jakarta.validation.Valid;
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
    public UserDto create(@RequestBody @Valid UserNewDto userNewDto) {
        return userMapper.toDto(userService.create(userNewDto));
    }

    @GetMapping
    public List<UserDto> findAll(@RequestParam(defaultValue = "0") int from,
                                 @RequestParam(defaultValue = "10") int size) {
        return userMapper.toDto(userService.findAll(from, size));
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return userMapper.toDto(userService.findById(id));
    }

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody @Valid UserNewDto newUser) {
        return userMapper.toDto(userService.update(id, newUser));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
