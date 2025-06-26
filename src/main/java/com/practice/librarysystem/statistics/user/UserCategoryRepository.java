package com.practice.librarysystem.statistics.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    Optional<UserCategory> findByUserIdAndCategoryId(long userId, long categoryId);

    Optional<UserCategory> findFirstByUserIdOrderByPopularity(Long userId);
}
