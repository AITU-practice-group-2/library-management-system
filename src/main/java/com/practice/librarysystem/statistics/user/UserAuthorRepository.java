package com.practice.librarysystem.statistics.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthorRepository extends JpaRepository<UserAuthor, Long> {
    Optional<UserAuthor> findByUserIdAndAuthorId(long userId, long authorId);

    Optional<UserAuthor> findFirstByUserIdOrderByPopularity(Long userId);
}
