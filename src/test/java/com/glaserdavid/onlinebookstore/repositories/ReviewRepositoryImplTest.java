package com.glaserdavid.onlinebookstore.repositories;

import com.glaserdavid.onlinebookstore.domain.Review;
import com.glaserdavid.onlinebookstore.exceptions.BadRequestException;
import com.glaserdavid.onlinebookstore.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ReviewRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ReviewRepositoryImpl reviewRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void create_ShouldReturnReviewId_WhenReviewCreatedSuccessfully() {
//        Review review = new Review(1, 1, 1, 5, "Great book!");
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        doAnswer(invocation -> {
//            PreparedStatementCreator psc = invocation.getArgument(0);
//            Connection connection = mock(Connection.class);
//            PreparedStatement ps = mock(PreparedStatement.class);
//            when(ps.executeUpdate()).thenReturn(1);
//            when(connection.prepareStatement(anyString(), anyInt())).thenReturn(ps);
//            psc.createPreparedStatement(connection);
//
//            // Simulate key generation
//            Map<String, Object> keys = new HashMap<>();
//            keys.put("review_id", 1);
//            keyHolder.getKeyList().add(keys);
//
//            return null;
//        }).when(jdbcTemplate).update(any(PreparedStatementCreator.class), eq(keyHolder));
//
//        Integer reviewId = reviewRepository.create(review);
//        assertNotNull(reviewId);
//        assertEquals(1, reviewId);
//
//        verify(jdbcTemplate, times(1)).update(any(PreparedStatementCreator.class), eq(keyHolder));
//    }

    @Test
    void create_ShouldThrowBadRequestException_WhenReviewCreationFails() {
        Review review = new Review(1, 1, 1, 5, "Great book!");

        doThrow(new RuntimeException()).when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));

        assertThrows(BadRequestException.class, () -> reviewRepository.create(review));
    }

    @Test
    void findReviewsByBookId_ShouldReturnReviews_WhenReviewsExist() {
        Review review = new Review(1, 1, 1, 5, "Great book!");

        when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(Collections.singletonList(review));

        List<Review> reviews = reviewRepository.findReviewsByBookId(1);
        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertEquals("Great book!", reviews.get(0).getComment());
    }

    @Test
    void findReviewsByBookId_ShouldThrowResourceNotFoundException_WhenNoReviewsExist() {
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> reviewRepository.findReviewsByBookId(1));
    }
}
