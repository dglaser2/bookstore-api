package com.glaserdavid.onlinebookstore.repositories;

import com.glaserdavid.onlinebookstore.domain.Review;
import com.glaserdavid.onlinebookstore.exceptions.BadRequestException;
import com.glaserdavid.onlinebookstore.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    private static final String SQL_CREATE = "INSERT INTO reviews (user_id, book_id, rating, comment) VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_BY_BOOK_ID = "SELECT * FROM reviews WHERE book_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(Review review) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, review.getUserId());
                ps.setInt(2, review.getBookId());
                ps.setInt(3, review.getRating());
                ps.setString(4, review.getComment());
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("review_id");
        } catch (Exception e) {
            throw new BadRequestException("Invalid review entry");
        }
    }

    @Override
    public List<Review> findReviewsByBookId(Integer bookId) {
        try {
            return jdbcTemplate.query(SQL_FIND_BY_BOOK_ID, new Object[]{bookId}, reviewRowMapper);
        } catch (Exception e) {
            throw new ResourceNotFoundException("No reviews found for book id " + bookId);
        }
    }

    private final RowMapper<Review> reviewRowMapper = (rs, rowNum) -> new Review(
            rs.getInt("review_id"),
            rs.getInt("user_id"),
            rs.getInt("book_id"),
            rs.getInt("rating"),
            rs.getString("comment")
    );
}
