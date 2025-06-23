package com.practice.librarysystem.review;

import com.practice.librarysystem.statistics.book.BookStatisticsService;
import com.practice.librarysystem.util.RequestConstants;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

@Slf4j
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    BookStatisticsService bookStatisticsService;
    private final Byte[] rateConstants = {-30, -15, -5, 5, 20};

    @GetMapping
    public List<ReviewResponseDTO> getAll(@RequestParam(defaultValue = "0") int from,
                                          @RequestParam(defaultValue = "10") int size,
                                          HttpServletRequest httpServletRequest) {
        String ip = RequestConstants.getClientIp(httpServletRequest);
        log.info("Endpoint GET: /reviews was accessed by IP:{}", ip);

        return reviewService.getAllReviews(from, size).stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ReviewResponseDTO getOne(@PathVariable Long id,
                                                    HttpServletRequest httpServletRequest) {
        String ip = RequestConstants.getClientIp(httpServletRequest);
        log.info("Endpoint GET: /reviews/{} was accessed by IP:{}", id, ip);

        Review review = reviewService.getReview(id);
        return reviewMapper.toDTO(review);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponseDTO createReview(@RequestBody @Valid ReviewRequestDTO dto,
                                          HttpServletRequest httpServletRequest,
                                          Principal principal) {
        String ip = RequestConstants.getClientIp(httpServletRequest);
        log.info("Endpoint POST: /reviews was accessed by IP:{}", ip);

        Review review = reviewService.createReview(dto);

        String email = principal.getName();
        int rating = review.getRating();
        bookStatisticsService.addPopularityToBook(rateConstants[rating - 1], review.getBook(), email);

        return reviewMapper.toDTO(review);
    }

    @GetMapping("/book/{bookId}")
    public List<ReviewResponseDTO> getReviewsByBook(@PathVariable Long bookId,
                                                    HttpServletRequest httpServletRequest) {
        String ip = RequestConstants.getClientIp(httpServletRequest);
        log.info("Endpoint GET: /reviews/book/{} was accessed by IP:{}", bookId, ip);

        return reviewService.getReviewsByBookId(bookId);
    }

    @PutMapping("/{id}")
    public ReviewResponseDTO update(@PathVariable Long id,
                                    @RequestBody ReviewRequestDTO dto,
                                    Principal principal,
                                    HttpServletRequest httpServletRequest) {
        String ip = RequestConstants.getClientIp(httpServletRequest);
        String email = principal.getName();
        log.info("Endpoint PUT: /reviews/{} was accessed by IP:{} by user:{}", id, ip, email);

        Review updatedReview = reviewService.updateReview(id, dto, email);

        int rating = updatedReview.getRating();
        bookStatisticsService.addPopularityToBook(rateConstants[rating] - 1, updatedReview.getBook(), email);

        return reviewMapper.toDTO(updatedReview);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable Long id,
                             Principal principal,
                             HttpServletRequest httpServletRequest) {
        String ip = RequestConstants.getClientIp(httpServletRequest);
        log.info("Endpoint DELETE: /reviews/{} was accessed by IP:{} by user:{}", id, ip, principal.getName());

        reviewService.deleteReview(id, principal.getName());
    }
}
