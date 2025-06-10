package com.practice.librarysystem.user;

import lombok.RequiredArgsConstructor;
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

        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
    }

    public User update(long id, UserNewDto newUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
        if (newUser.getEmail() != null) {
            user.setEmail(user.getEmail());
        }
        if (newUser.getLogin() != null) {
            user.setLogin(user.getLogin());
        }
        if (newUser.getPassword() != null) {
            user.setPassword(user.getPassword());
        }

        return userRepository.save(user);
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
