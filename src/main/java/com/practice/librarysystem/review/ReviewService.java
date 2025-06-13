package com.practice.librarysystem.review;

import com.practice.librarysystem.book.Book;
import com.practice.librarysystem.book.BookRepository;
import com.practice.librarysystem.exception.NotFoundException;
import com.practice.librarysystem.user.User;
import com.practice.librarysystem.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;


    public Page<Review> getAllReviews(int from, int size) {
        int pageNumber = from / size;

        Pageable pageable = PageRequest.of(pageNumber, size);

        return reviewRepository.findAll(pageable);
    }

    public Review getReview(Long id) {
        return reviewRepository.findById(id).orElseThrow();
    }

    public Review createReview(ReviewRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new NotFoundException("Book not found"));

        Review review = new Review();
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());
        review.setCreatedAt(LocalDateTime.now());
        review.setUser(user); 
        review.setBook(book);    

        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, ReviewRequestDTO dto) {
        Review existing = getReview(id);
        existing.setComment(dto.getComment());
        existing.setRating(dto.getRating());
        return reviewRepository.save(existing);
    }


    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
