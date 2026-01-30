package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.examly.springapp.model.Book;

public interface BookService {

    Book create(Book book);

    List<Book> showAll();

    Book showById(Long id);

    Book update(Long id, Book book);

    void delete(Long id);

    List<Book> getByCategory(String categoryName);

    List<Book> getByTitle(String title);

    Page<Book> pagination(int pgNo, int pgSize);

    Page<Book> pageswithfield(int pgNo, int pgSize, String field);

    List<Book> filterByField(String field, String value);
}
