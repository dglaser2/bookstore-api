package com.glaserdavid.onlinebookstore.services;

import com.glaserdavid.onlinebookstore.domain.Review;
import com.glaserdavid.onlinebookstore.exceptions.BadRequestException;
import com.glaserdavid.onlinebookstore.exceptions.ResourceNotFoundException;
import com.glaserdavid.onlinebookstore.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review createReview(Review review) throws BadRequestException {
        try {
            Integer reviewId = reviewRepository.create(review);
            return reviewRepository.findReviewsByBookId(review.getBookId()).stream()
                    .filter(r -> r.getReviewId().equals(reviewId))
                    .findFirst()
                    .orElseThrow(() -> new BadRequestException("Review not created"));
        } catch (Exception e) {
            throw new BadRequestException("Failed to create review: " + e.getMessage());
        }
    }

    @Override
    public List<Review> getReviewsByBookId(Integer bookId) throws ResourceNotFoundException {
        try {
            List<Review> reviews = reviewRepository.findReviewsByBookId(bookId);
            if (reviews.isEmpty()) {
                throw new ResourceNotFoundException("No reviews found for book id " + bookId);
            }
            return reviews;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Failed to retrieve reviews for book id " + bookId + ": " + e.getMessage());
        }
    }
}
