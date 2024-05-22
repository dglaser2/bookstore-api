package com.glaserdavid.onlinebookstore.services;

import com.glaserdavid.onlinebookstore.domain.Review;
import com.glaserdavid.onlinebookstore.exceptions.BadRequestException;
import com.glaserdavid.onlinebookstore.exceptions.ResourceNotFoundException;
import com.glaserdavid.onlinebookstore.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createReview_ShouldReturnReview_WhenReviewIsCreated() throws BadRequestException {
        Review review = new Review(1, 1, 1, 5, "Great book!");
        when(reviewRepository.create(any(Review.class))).thenReturn(1);
        when(reviewRepository.findReviewsByBookId(anyInt())).thenReturn(Collections.singletonList(review));

        Review createdReview = reviewService.createReview(review);

        assertNotNull(createdReview);
        assertEquals(1, createdReview.getReviewId());
        assertEquals("Great book!", createdReview.getComment());
    }

    @Test
    void createReview_ShouldThrowBadRequestException_WhenReviewNotCreated() {
        Review review = new Review(1, 1, 1, 5, "Great book!");
        when(reviewRepository.create(any(Review.class))).thenThrow(new BadRequestException("Review not created"));

        assertThrows(BadRequestException.class, () -> {
            reviewService.createReview(review);
        });
    }

    @Test
    void getReviewsByBookId_ShouldReturnReviews_WhenReviewsExist() throws ResourceNotFoundException {
        Review review = new Review(1, 1, 1, 5, "Great book!");
        when(reviewRepository.findReviewsByBookId(anyInt())).thenReturn(Collections.singletonList(review));

        List<Review> reviews = reviewService.getReviewsByBookId(1);

        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertEquals("Great book!", reviews.get(0).getComment());
    }

    @Test
    void getReviewsByBookId_ShouldThrowResourceNotFoundException_WhenNoReviewsFound() {
        when(reviewRepository.findReviewsByBookId(anyInt())).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            reviewService.getReviewsByBookId(1);
        });
    }

    @Test
    void getReviewsByBookId_ShouldThrowResourceNotFoundException_WhenExceptionOccurs() {
        when(reviewRepository.findReviewsByBookId(anyInt())).thenThrow(new RuntimeException("Database error"));

        assertThrows(ResourceNotFoundException.class, () -> {
            reviewService.getReviewsByBookId(1);
        });
    }
}
