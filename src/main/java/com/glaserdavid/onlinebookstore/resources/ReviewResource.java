package com.glaserdavid.onlinebookstore.resources;

import com.glaserdavid.onlinebookstore.domain.Review;
import com.glaserdavid.onlinebookstore.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewResource {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/")
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review createdReview = reviewService.createReview(review);
        return ResponseEntity.ok(createdReview);
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<List<Review>> getReviewsByBookId(@PathVariable Integer bookId) {
        List<Review> reviews = reviewService.getReviewsByBookId(bookId);
        return ResponseEntity.ok(reviews);
    }
}
