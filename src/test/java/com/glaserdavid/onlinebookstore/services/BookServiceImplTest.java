package com.glaserdavid.onlinebookstore.services;

import com.glaserdavid.onlinebookstore.domain.Book;
import com.glaserdavid.onlinebookstore.exceptions.BadRequestException;
import com.glaserdavid.onlinebookstore.exceptions.ResourceNotFoundException;
import com.glaserdavid.onlinebookstore.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBooks_ShouldReturnBooks_WhenBooksExist() {
        Book book = new Book(1, "Test Title", "Test Author", "Test Description", 9.99f, 10);
        when(bookRepository.findAllPaginate(anyInt(), anyInt())).thenReturn(Collections.singletonList(book));

        List<Book> books = bookService.getAllBooks(1, 10);

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("Test Title", books.get(0).getTitle());
    }

    @Test
    void getAllBooks_ShouldThrowBadRequestException_WhenInvalidPageNumber() {
        assertThrows(BadRequestException.class, () -> bookService.getAllBooks(-1, 10));
        assertThrows(BadRequestException.class, () -> bookService.getAllBooks(1, -10));
        assertThrows(BadRequestException.class, () -> bookService.getAllBooks(0, 10)); // Edge case
        assertThrows(BadRequestException.class, () -> bookService.getAllBooks(1, 0));  // Edge case
    }

    @Test
    void getBookById_ShouldReturnBook_WhenBookExists() {
        Book book = new Book(1, "Test Title", "Test Author", "Test Description", 9.99f, 10);
        when(bookRepository.findById(anyInt())).thenReturn(book);

        Book foundBook = bookService.getBookById(1);

        assertNotNull(foundBook);
        assertEquals("Test Title", foundBook.getTitle());
    }

    @Test
    void getBookById_ShouldThrowResourceNotFoundException_WhenBookDoesNotExist() {
        when(bookRepository.findById(anyInt())).thenThrow(new ResourceNotFoundException("Book not found"));

        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(1));
    }

    @Test
    void searchBooks_ShouldReturnBooks_WhenBooksExist() {
        Book book = new Book(1, "Test Title", "Test Author", "Test Description", 9.99f, 10);
        when(bookRepository.search(anyString())).thenReturn(Collections.singletonList(book));

        List<Book> books = bookService.searchBooks("Test");

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("Test Title", books.get(0).getTitle());
    }

    @Test
    void searchBooks_ShouldReturnEmptyList_WhenNoBooksMatch() {
        when(bookRepository.search(anyString())).thenReturn(Collections.emptyList());

        List<Book> books = bookService.searchBooks("NonExistingTitle");

        assertNotNull(books);
        assertTrue(books.isEmpty());
    }

    @Test
    void getAllBooks_ShouldReturnEmptyList_WhenNoBooksExist() {
        when(bookRepository.findAllPaginate(anyInt(), anyInt())).thenReturn(Collections.emptyList());

        List<Book> books = bookService.getAllBooks(1, 10);

        assertNotNull(books);
        assertTrue(books.isEmpty());
    }
}
