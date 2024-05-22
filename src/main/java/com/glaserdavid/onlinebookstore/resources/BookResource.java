package com.glaserdavid.onlinebookstore.resources;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookResource {

    @GetMapping("")
    public String getAllBooks(HttpServletRequest request) {
        int userId = (Integer) request.getAttribute("user_id");
        return "Authenticated! User ID: " + userId;
    }
}
