package com.practice.librarysystem.review;

import com.practice.librarysystem.util.RequestConstants;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    public ReviewController(ReviewService reviewService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

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
    public ResponseEntity<ReviewResponseDTO> getOne(@PathVariable Long id,
                                                    HttpServletRequest httpServletRequest) {
        String ip = RequestConstants.getClientIp(httpServletRequest);
        log.info("Endpoint GET: /reviews/{} was accessed by IP:{}", id, ip);

        Review review = reviewService.getReview(id);
        return ResponseEntity.ok(reviewMapper.toDTO(review));
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> createReview(@RequestBody @Valid ReviewRequestDTO dto,
                                                          HttpServletRequest httpServletRequest) {
        String ip = RequestConstants.getClientIp(httpServletRequest);
        log.info("Endpoint POST: /reviews was accessed by IP:{}", ip);

        Review review = reviewService.createReview(dto);
        return ResponseEntity.ok(reviewMapper.toDTO(review));
    }

    @GetMapping("/book/{bookId}")
    public List<ReviewResponseDTO> getReviewsByBook(@PathVariable Long bookId,
                                                    HttpServletRequest httpServletRequest) {
        String ip = RequestConstants.getClientIp(httpServletRequest);
        log.info("Endpoint GET: /reviews/book/{} was accessed by IP:{}", bookId, ip);

        return reviewService.getReviewsByBookId(bookId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> update(@PathVariable Long id,
                                                    @RequestBody ReviewRequestDTO dto,
                                                    Principal principal,
                                                    HttpServletRequest httpServletRequest) {
        String ip = RequestConstants.getClientIp(httpServletRequest);
        log.info("Endpoint PUT: /reviews/{} was accessed by IP:{} by user:{}", id, ip, principal.getName());

        Review updatedReview = reviewService.updateReview(id, dto, principal.getName());
        return ResponseEntity.ok(reviewMapper.toDTO(updatedReview));
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
