package com.practice.librarysystem.controller;

import com.practice.librarysystem.dto.ReviewResponseDTO;
import com.practice.librarysystem.entity.Review;
import com.practice.librarysystem.mapper.ReviewMapper;
import com.practice.librarysystem.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    public ReviewController(ReviewService reviewService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    @GetMapping
    public List<ReviewResponseDTO> getAll() {
        return reviewService.getAllReviews().stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Review getOne(@PathVariable Long id) {
        return reviewService.getReview(id);
    }

    @PostMapping
    public Review create(@RequestBody Review review) {
        return reviewService.createReview(review);
    }

    @PutMapping("/{id}")
    public Review update(@PathVariable Long id, @RequestBody Review review) {
        return reviewService.updateReview(id, review);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}

