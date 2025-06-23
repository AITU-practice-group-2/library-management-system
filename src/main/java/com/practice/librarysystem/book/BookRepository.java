package com.practice.librarysystem.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findAllByTitleContainingOrderByPopularity(String search, Pageable pageable);

    Page<Book> findAllByAuthor_IdOrderByPopularity(Long authorId, Pageable pageable);

    Page<Book> findAllByCategory_IdOrderByPopularity(Long categoryId, Pageable pageable);

    Page<Book> findAllByTitleContainingAndAuthor_IdOrderByPopularity(String search, Long authorId, Pageable pageable);

    Page<Book> findAllByTitleContainingAndCategory_IdOrderByPopularity(String search, Long categoryId, Pageable pageable);

    Page<Book> findAllByAuthor_IdAndCategory_IdOrderByPopularity(Long authorId, Long categoryId, Pageable pageable);

    Page<Book> findAllByTitleContainingAndAuthor_IdAndCategory_IdOrderByPopularity(String search, Long authorId, Long categoryId, Pageable pageable);

    Page<Book> findAllByOrderByPopularityDesc(Pageable pageable);
}
