package com.glaserdavid.onlinebookstore.repositories;

import com.glaserdavid.onlinebookstore.domain.Book;

import java.util.List;

public interface BookRepository {

    public List<Book> findAllPaginate(int limit, int offset);

    public Book findById(Integer bookId);

    public List<Book> search(String query);
}
