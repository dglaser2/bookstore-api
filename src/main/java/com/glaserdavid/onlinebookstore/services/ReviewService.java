package com.glaserdavid.onlinebookstore.services;

import com.glaserdavid.onlinebookstore.domain.Review;
import com.glaserdavid.onlinebookstore.exceptions.BadRequestException;
import com.glaserdavid.onlinebookstore.exceptions.ResourceNotFoundException;
import java.util.List;

public interface ReviewService {

    Review createReview(Review review) throws BadRequestException;

    List<Review> getReviewsByBookId(Integer bookId) throws ResourceNotFoundException;
}
