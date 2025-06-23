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
import org.springframework.security.access.AccessDeniedException;
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
        return reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review not found with id: " + id));
    }

    public List<ReviewResponseDTO> getReviewsByBookId(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found with id: " + bookId));

        List<Review> reviews = reviewRepository.findByBookId(bookId);

        return reviews.stream().map(review -> {
            ReviewResponseDTO dto = new ReviewResponseDTO();
            dto.setId(review.getId());
            dto.setComment(review.getComment());
            dto.setRating(review.getRating());
            dto.setBookTitle(book.getTitle());
            dto.setUserName(review.getUser().getLogin());
            dto.setUserId(review.getUser().getId());
            dto.setCreatedAt(review.getCreatedAt());
            return dto;
        }).toList();
    }


    public Review createReview(ReviewRequestDTO dto) {
        System.out.println("Creating review for userId=" + dto.getUserId() + ", bookId=" + dto.getBookId());

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + dto.getUserId()));

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new NotFoundException("Book not found with id: " + dto.getBookId()));

        Review review = new Review();
        review.setUser(user);
        review.setBook(book);
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());
        review.setCreatedAt(LocalDateTime.now());

        Review saved = reviewRepository.save(review);
        System.out.println("Saved review with id: " + saved.getId());

        return saved;
    }

    public Review updateReview(Long id, ReviewRequestDTO dto, String email) {
        Review existing = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review not found with id: " + id));

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email = " + email + " not found."));

        if (existing.getUser().getId() != currentUser.getId()) {
            throw new AccessDeniedException("You are not allowed to update this review");
        }

        existing.setId(id);
        existing.setComment(dto.getComment());
        existing.setRating(dto.getRating());
        return reviewRepository.save(existing);
    }

    public void deleteReview(Long id, String email) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review not found with id: " + id));

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email = " + email + " not found."));

        if (review.getUser().getId() != currentUser.getId()) {
            throw new AccessDeniedException("You are not allowed to delete this review");
        }


        reviewRepository.delete(review);
    }


}
