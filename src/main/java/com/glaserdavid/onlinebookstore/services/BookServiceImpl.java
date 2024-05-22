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
        } catch (Exception e) {
            throw new BadRequestException("Unable to retrieve books");
        }
    }

    @Override
    public Book getBookById(Integer bookId) throws ResourceNotFoundException {
        try {
            return bookRepository.findById(bookId);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Book not found with id " + bookId);
        } catch (Exception e) {
            throw new BadRequestException("Unable to fetch book details");
        }
    }

    @Override
    public List<Book> searchBooks(String query) {
        return bookRepository.search(query);
    }
}
