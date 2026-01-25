package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.Book;

public interface BookService {

    Book create(Book book);

    List<Book> showAll();

    Book showById(Long id);

    Book update(Long id, Book book);

    void delete(Long id);

    List<Book> getByCategory(String categoryName);

    List<Book> getByTitle(String title);
}
