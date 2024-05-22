package com.glaserdavid.onlinebookstore.resources;

import com.glaserdavid.onlinebookstore.domain.Review;
import com.glaserdavid.onlinebookstore.services.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewResource.class)
class ReviewResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void addReview_ShouldReturnCreatedReview() throws Exception {
        Review review = new Review(1, 1, 1, 5, "Great book!");
        when(reviewService.createReview(any(Review.class))).thenReturn(review);

        String reviewJson = "{ \"userId\": 1, \"bookId\": 1, \"rating\": 5, \"comment\": \"Great book!\" }";

        mockMvc.perform(post("/api/reviews/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reviewJson))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    assertTrue(jsonResponse.contains("\"comment\":\"Great book!\""));
                });
    }

    @Test
    void getReviewsByBookId_ShouldReturnReviews() throws Exception {
        Review review = new Review(1, 1, 1, 5, "Great book!");
        List<Review> reviews = Collections.singletonList(review);
        when(reviewService.getReviewsByBookId(anyInt())).thenReturn(reviews);

        mockMvc.perform(get("/api/reviews/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    assertTrue(jsonResponse.contains("\"comment\":\"Great book!\""));
                });
    }
}
