package com.practice.librarysystem.user.statistics;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    Optional<UserCategory> findByUserIdAndCategoryId(long userId, long categoryId);
}
