package com.practice.librarysystem.user;

import com.practice.librarysystem.util.RequestConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid UserNewDto userNewDto,
                          HttpServletRequest httpServletRequest) {
        String ip = RequestConstants.getClientIp(httpServletRequest);
        log.info("Endpoint POST: /users was accessed by IP:{}", ip);
        return userMapper.toDto(userService.create(userNewDto));
    }

    @GetMapping
    public List<UserDto> findAll(@RequestParam(defaultValue = "0") int from,
                                 @RequestParam(defaultValue = "10") int size,
                                 HttpServletRequest httpServletRequest) {
        String ip = RequestConstants.getClientIp(httpServletRequest);
        log.info("Endpoint GET: /users was accessed by IP:{}", ip);
        return userMapper.toDto(userService.findAll(from, size));
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id,
                            HttpServletRequest httpServletRequest) {
        String ip = RequestConstants.getClientIp(httpServletRequest);
        log.info("Endpoint GET: /users/{id} was accessed by IP:{}", ip);
        return userMapper.toDto(userService.findById(id));
    }

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody @Valid UserNewDto newUser,
                          HttpServletRequest httpServletRequest) {
        String ip = RequestConstants.getClientIp(httpServletRequest);
        log.info("Endpoint PATCH: /users/{id} was accessed by IP:{}", ip);
        return userMapper.toDto(userService.update(id, newUser));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id,
                       HttpServletRequest httpServletRequest) {
        String ip = RequestConstants.getClientIp(httpServletRequest);
        log.info("Endpoint DELETE: /users/{id} was accessed by IP:{}", ip);
        userService.delete(id);
    }

    @GetMapping("/me")
    public ResponseEntity<Long> getCurrentUserId(Principal principal,
                                                 HttpServletRequest httpServletRequest) {
        String ip = RequestConstants.getClientIp(httpServletRequest);
        log.info("Endpoint GET: /users/me was accessed by IP:{}", ip);
        String email = principal.getName();
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(user.getId());
    }
    @GetMapping("/role")
    public ResponseEntity<String> getCurrentUserRole(Principal principal,
                                                     HttpServletRequest httpServletRequest) {
        String ip = RequestConstants.getClientIp(httpServletRequest);
        log.info("Endpoint GET: /users/role was accessed by IP:{} by user:{}", ip, principal.getName());

        User user = userService.findByEmail(principal.getName());

        return ResponseEntity.ok(user.getRole().name());
    }

}
