package com.practice.librarysystem.user;

import com.practice.librarysystem.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User create(UserNewDto newUser) {
        User user = new User();
        user.setEmail(newUser.getEmail());
        user.setLogin(newUser.getLogin());
        user.setPassword(newUser.getPassword());
        if (newUser.getRole().equalsIgnoreCase("ADMIN")) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.GUEST);
        }
        userRepository.save(user);
        return user;
    }

    public List<User> findAll(int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return userRepository.findAll(pageable).getContent();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found."));
    }

    public User update(Long id, UserNewDto newUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found."));
        if (newUser.getEmail() != null) {
            user.setEmail(user.getEmail());
        }
        if (newUser.getLogin() != null) {
            user.setLogin(user.getLogin());
        }
        if (newUser.getPassword() != null) {
            user.setPassword(user.getPassword());
        }
        if (newUser.getRole() != null) {
            if (newUser.getRole().equalsIgnoreCase("ADMIN")) {
                user.setRole(Role.ADMIN);
            } else {
                user.setRole(Role.GUEST);
            }
        }

        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
