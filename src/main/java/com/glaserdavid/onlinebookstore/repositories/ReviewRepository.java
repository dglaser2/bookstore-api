package com.glaserdavid.onlinebookstore.repositories;

import com.glaserdavid.onlinebookstore.domain.Review;
import java.util.List;

public interface ReviewRepository {

    Integer create(Review review);

    List<Review> findReviewsByBookId(Integer bookId);
}
