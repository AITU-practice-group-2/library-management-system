package com.practice.librarysystem.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/me")
    public ResponseEntity<Long> getCurrentUserId(Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(user.getId());
    }
}
