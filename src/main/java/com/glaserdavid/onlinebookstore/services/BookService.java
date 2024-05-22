package com.glaserdavid.onlinebookstore.services;

import com.glaserdavid.onlinebookstore.domain.Book;
import com.glaserdavid.onlinebookstore.exceptions.BadRequestException;
import com.glaserdavid.onlinebookstore.exceptions.ResourceNotFoundException;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks(Integer page, Integer size) throws BadRequestException;

    Book getBookById(Integer bookId) throws ResourceNotFoundException;

    List<Book> searchBooks(String query) throws BadRequestException;
}
