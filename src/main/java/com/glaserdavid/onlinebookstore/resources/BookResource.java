package com.glaserdavid.onlinebookstore.resources;

import com.glaserdavid.onlinebookstore.domain.Book;
import com.glaserdavid.onlinebookstore.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookResource {

    @Autowired
    private BookService bookService;

    @Operation(summary = "Get all books")
    @GetMapping("")
    public ResponseEntity<List<Book>> getAllBooks(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        List<Book> books = bookService.getAllBooks(page, size);
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Get book by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @Operation(summary = "Search books")
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam("query") String query) {
        List<Book> books = bookService.searchBooks(query);
        return ResponseEntity.ok(books);
    }
}
