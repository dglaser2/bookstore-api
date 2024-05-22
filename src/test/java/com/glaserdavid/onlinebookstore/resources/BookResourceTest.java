package com.glaserdavid.onlinebookstore.resources;

import com.glaserdavid.onlinebookstore.domain.Book;
import com.glaserdavid.onlinebookstore.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookResource.class)
class BookResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBooks_ShouldReturnBooks() throws Exception {
        Book book = new Book(1, "Test Title", "Test Author", "Test Description", 9.99f, 10);
        when(bookService.getAllBooks(anyInt(), anyInt())).thenReturn(Collections.singletonList(book));

        // Example JWT-generated token
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTY0MDA5NDQsImV4cCI6MTcxNjQwODE0NCwidXNlcl9pZCI6MTQsInVzZXJuYW1" +
                "lIjoidGVzdHVzZXIyMCIsImVtYWlsIjoiYWxpc29uQHRlc3QuY29tIn0.TDtN1HKWWroBfK1rDBbM3gsCIa3KEvgTix71C9ZkSqA";

        mockMvc.perform(get("/api/books?page=1&size=10")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'bookId':1,'title':'Test Title','author':'Test Author','description':'Test Description','price':9.99,'quantity':10}]"));
    }
}
