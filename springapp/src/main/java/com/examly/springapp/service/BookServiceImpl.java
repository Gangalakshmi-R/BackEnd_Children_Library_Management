package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.examly.springapp.exception.EmptyDataException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Book;
import com.examly.springapp.repository.BookRepo;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bkRepo;

    public Book create(Book book) {
        return bkRepo.save(book);
    }

    public List<Book> showAll() {
        List<Book> list = bkRepo.findAll();
        if (list.isEmpty()) {
            throw new EmptyDataException("No books found");
        }
        return list;
    }

    public Book showById(Long id) {
        return bkRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found with id: " + id));
    }

    public Book update(Long id, Book book) {
        Book existing = showById(id);

        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setAvailable(book.getAvailable());
        existing.setBookCategory(book.getBookCategory());

        return bkRepo.save(existing);
    }

    public void delete(Long id) {
        bkRepo.delete(showById(id));
    }

    public List<Book> getByCategory(String categoryName) {
        List<Book> list = bkRepo.findByBookCategoryCategoryName(categoryName);
        if (list.isEmpty()) {
            throw new EmptyDataException("No books found for category: " + categoryName);
        }
        return list;
    }

    public List<Book> getByTitle(String title) {
        List<Book> list = bkRepo.findByTitle(title);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Book not found with title: " + title);
        }
        return list;
    }

    public Page<Book> pagination(int pgNo, int pgSize) {
        return bkRepo.findAll(PageRequest.of(pgNo, pgSize));
    }

    public Page<Book> pageswithfield(int pgNo, int pgSize, String field) {
        Pageable pg = PageRequest.of(pgNo, pgSize, Sort.by(field).ascending());
        return bkRepo.findAll(pg);
    }

    public List<Book> filterByField(String field, String value) {

        List<Book> list;

        switch (field.toLowerCase()) {
            case "title":
                list = bkRepo.findByTitle(value);
                break;

            case "author":
                list = bkRepo.findByAuthor(value);
                break;

            case "available":
                list = bkRepo.findByAvailable(Boolean.parseBoolean(value));
                break;

            case "category":
                list = bkRepo.findByBookCategoryCategoryName(value);
                break;

            default:
                list = List.of();
        }

        if (list.isEmpty()) {
            throw new EmptyDataException("No matching books found");
        }

        return list;
    }
}
