package com.glaserdavid.onlinebookstore.repositories;

import com.glaserdavid.onlinebookstore.domain.Book;
import com.glaserdavid.onlinebookstore.exceptions.BadRequestException;

import java.util.List;

public interface BookRepository {

    public List<Book> findAllPaginate(int limit, int offset) throws BadRequestException;

    public Book findById(Integer bookId) throws BadRequestException;

    public List<Book> search(String query) throws BadRequestException;
}
