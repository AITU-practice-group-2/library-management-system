package com.practice.librarysystem.user.statistics;

import com.practice.librarysystem.author.Author;
import com.practice.librarysystem.category.Category;
import com.practice.librarysystem.exception.NotFoundException;
import com.practice.librarysystem.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PopularityService {
    UserAuthorRepository userAuthorRepository;
    UserCategoryRepository userCategoryRepository;

    public void addAuthorPopularity(int value, User user, Author author) {
        if (userAuthorRepository.findByUserIdAndAuthorId(user.getId(), author.getId()).isEmpty()) {
            UserAuthor userAuthor = new UserAuthor();
            userAuthor.setAuthor(author);
            userAuthor.setUser(user);
            userAuthor.setPopularity(value);

            userAuthorRepository.save(userAuthor);
        } else if (userAuthorRepository.findByUserIdAndAuthorId(user.getId(), author.getId()).isPresent()) {
            UserAuthor userAuthor = userAuthorRepository.findByUserIdAndAuthorId(user.getId(), author.getId())
                    .orElseThrow(() -> new NotFoundException("Not found."));
            userAuthor.setPopularity(userAuthor.getPopularity() + value);

            userAuthorRepository.save(userAuthor);
        }
    }

    public void subtractAuthorPopularity(int value, User user, Author author) {
        if (userAuthorRepository.findByUserIdAndAuthorId(user.getId(), author.getId()).isEmpty()) {
            UserAuthor userAuthor = new UserAuthor();
            userAuthor.setAuthor(author);
            userAuthor.setUser(user);
            userAuthor.setPopularity(-value);

            userAuthorRepository.save(userAuthor);
        } else if (userAuthorRepository.findByUserIdAndAuthorId(user.getId(), author.getId()).isPresent()) {
            UserAuthor userAuthor = userAuthorRepository.findByUserIdAndAuthorId(user.getId(), author.getId())
                    .orElseThrow(() -> new NotFoundException("Not found."));
            userAuthor.setPopularity(userAuthor.getPopularity() - value);

            userAuthorRepository.save(userAuthor);
        }
    }

    public void addCategoryPopularity(int value, User user, Category category) {
        if (userCategoryRepository.findByUserIdAndCategoryId(user.getId(), category.getId()).isEmpty()) {
            UserCategory userCategory = new UserCategory();
            userCategory.setCategory(category);
            userCategory.setUser(user);
            userCategory.setPopularity(value);

            userCategoryRepository.save(userCategory);
        } else if (userCategoryRepository.findByUserIdAndCategoryId(user.getId(), category.getId()).isPresent()) {
            UserCategory userCategory = userCategoryRepository.findByUserIdAndCategoryId(user.getId(), category.getId())
                    .orElseThrow(() -> new NotFoundException("Not found"));

            userCategory.setPopularity(userCategory.getPopularity() + value);

            userCategoryRepository.save(userCategory);
        }
    }

    public void subtractCategoryPopularity(int value, User user, Category category) {
        if (userCategoryRepository.findByUserIdAndCategoryId(user.getId(), category.getId()).isEmpty()) {
            UserCategory userCategory = new UserCategory();
            userCategory.setCategory(category);
            userCategory.setUser(user);
            userCategory.setPopularity(-value);

            userCategoryRepository.save(userCategory);
        } else if (userCategoryRepository.findByUserIdAndCategoryId(user.getId(), category.getId()).isPresent()) {
            UserCategory userCategory = userCategoryRepository.findByUserIdAndCategoryId(user.getId(), category.getId())
                    .orElseThrow(() -> new NotFoundException("Not found"));

            userCategory.setPopularity(userCategory.getPopularity() - value);

            userCategoryRepository.save(userCategory);
        }
    }
}
