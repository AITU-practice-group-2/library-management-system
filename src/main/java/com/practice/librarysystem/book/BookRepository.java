package com.practice.librarysystem.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findAllByTitleContaining(String search, Pageable pageable);

    Page<Book> findAllByAuthor_Id(Long authorId, Pageable pageable);

    Page<Book> findAllByCategory_Id(Long categoryId, Pageable pageable);

    Page<Book> findAllByTitleContainingAndAuthor_Id(String search, Long authorId, Pageable pageable);

    Page<Book> findAllByTitleContainingAndCategory_Id(String search, Long categoryId, Pageable pageable);

    Page<Book> findAllByAuthor_IdAndCategory_Id(Long authorId, Long categoryId, Pageable pageable);

    Page<Book> findAllByTitleContainingAndAuthor_IdAndCategory_Id(String search, Long authorId, Long categoryId, Pageable pageable);

    Page<Book> findAllByAvailableGreaterThanOrderByPopularity(int AvailableNum, Pageable pageable);

    List<Book> findTop3ByAuthor_IdAndAvailableGreaterThanOrCategory_IdAndAvailableGreaterThan(Long authorId, int availableNum1, Long categoryId, int availableNum2);
}
