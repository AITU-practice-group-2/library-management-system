package com.practice.librarysystem.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

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
                                          @RequestParam(defaultValue = "10") int size) {
        return reviewService.getAllReviews(from, size).stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> getOne(@PathVariable Long id) {
        Review review = reviewService.getReview(id);
        return ResponseEntity.ok(reviewMapper.toDTO(review));
    }


   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReviewResponseDTO> createReview(@RequestBody @Valid ReviewRequestDTO dto) {
        Review review = reviewService.createReview(dto);
        ReviewResponseDTO response = reviewMapper.toDTO(review);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid ReviewRequestDTO dto) {

        Review updatedReview = reviewService.updateReview(id, dto);
        return ResponseEntity.ok(reviewMapper.toDTO(updatedReview));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}

