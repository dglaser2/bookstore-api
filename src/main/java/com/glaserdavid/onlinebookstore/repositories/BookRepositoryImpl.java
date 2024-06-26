package com.glaserdavid.onlinebookstore.repositories;

import com.glaserdavid.onlinebookstore.domain.Book;
import com.glaserdavid.onlinebookstore.exceptions.BadRequestException;
import com.glaserdavid.onlinebookstore.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {


    private static final String SQL_FIND_ALL = "SELECT b.book_id, b.title, a.name AS author, b.description, b.price, b.quantity " +
            "FROM books b JOIN authors a ON b.author_id = a.author_id LIMIT ? OFFSET ?";
    private static final String SQL_FIND_BY_ID = "SELECT b.book_id, b.title, a.name AS author, b.description, b.price, b.quantity " +
            "FROM books b JOIN authors a ON b.author_id = a.author_id WHERE b.book_id = ?";
    private static final String SQL_SEARCH = "SELECT b.book_id, b.title, a.name AS author, b.description, b.price, b.quantity " +
            "FROM books b JOIN authors a ON b.author_id = a.author_id " +
            "WHERE b.title ILIKE ? OR a.name ILIKE ? OR b.description ILIKE ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Book> findAllPaginate(int limit, int offset) {
        try {
            return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{limit, offset}, bookRowMapper);
        } catch (Exception e) {
            throw new BadRequestException("Error retrieving paginated list of books");
        }
    }

    public Book findById(Integer bookId) {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{bookId}, bookRowMapper);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Book with id " + bookId + " not found");
        }
    }

    public List<Book> search(String query) {
        try {
            String searchQuery = "%" + query + "%";
            return jdbcTemplate.query(SQL_SEARCH, new Object[]{searchQuery, searchQuery, searchQuery}, bookRowMapper);
        } catch (Exception e) {
            throw new BadRequestException("Error searching for books with query: " + query);
        }
    }

    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> new Book(
            rs.getInt("book_id"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("description"),
            rs.getFloat("price"),
            rs.getInt("quantity")
    );
}
