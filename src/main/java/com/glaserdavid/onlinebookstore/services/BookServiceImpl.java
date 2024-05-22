package com.glaserdavid.onlinebookstore.services;

import com.glaserdavid.onlinebookstore.domain.Book;
import com.glaserdavid.onlinebookstore.exceptions.AuthException;
import com.glaserdavid.onlinebookstore.exceptions.BadRequestException;
import com.glaserdavid.onlinebookstore.exceptions.ResourceNotFoundException;
import com.glaserdavid.onlinebookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks(Integer page, Integer size) throws BadRequestException {
        try {
            int offset = (page - 1) * size;
            return bookRepository.findAllPaginate(size, offset);
        } catch (IndexOutOfBoundsException e) {
            throw new BadRequestException("Invalid page number");
        }
    }

    @Override
    public Book getBookById(Integer bookId) throws ResourceNotFoundException {
        return bookRepository.findById(bookId);
    }

    @Override
    public List<Book> searchBooks(String query) {
        return bookRepository.search(query);
    }
}
