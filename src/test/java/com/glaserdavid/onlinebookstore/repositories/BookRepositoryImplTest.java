package com.glaserdavid.onlinebookstore.repositories;

import com.glaserdavid.onlinebookstore.domain.Book;
import com.glaserdavid.onlinebookstore.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class BookRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookRepositoryImpl bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllPaginate_ShouldReturnBooks() {
        Book book = new Book(1, "Test Title", "Test Author", "Test Description", 9.99f, 10);
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(Collections.singletonList(book));

        List<Book> books = bookRepository.findAllPaginate(10, 0);

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("Test Title", books.get(0).getTitle());
    }

    @Test
    void findById_ShouldReturnBook_WhenBookExists() {
        Book book = new Book(1, "Test Title", "Test Author", "Test Description", 9.99f, 10);
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(book);

        Book foundBook = bookRepository.findById(1);

        assertNotNull(foundBook);
        assertEquals("Test Title", foundBook.getTitle());
    }

    @Test
    void findById_ShouldThrowResourceNotFoundException_WhenBookDoesNotExist() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenThrow(new ResourceNotFoundException("Book not found"));

        assertThrows(ResourceNotFoundException.class, () -> {
            bookRepository.findById(1);
        });
    }

    @Test
    void search_ShouldReturnBooks_WhenBooksExist() {
        Book book = new Book(1, "Test Title", "Test Author", "Test Description", 9.99f, 10);
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(Collections.singletonList(book));

        List<Book> books = bookRepository.search("Test");

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("Test Title", books.get(0).getTitle());
    }
}
