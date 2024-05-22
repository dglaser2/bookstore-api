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
        Integer reviewId = reviewRepository.create(review);
        return reviewRepository.findReviewsByBookId(review.getBookId()).stream()
                .filter(r -> r.getReviewId().equals(reviewId))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Review not created"));
    }

    @Override
    public List<Review> getReviewsByBookId(Integer bookId) throws ResourceNotFoundException {
        return reviewRepository.findReviewsByBookId(bookId);
    }
}
