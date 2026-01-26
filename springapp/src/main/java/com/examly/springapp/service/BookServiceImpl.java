package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Book;
import com.examly.springapp.repository.BookRepo;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bkRepo;


    public Book create(Book book) {
        try {
            return bkRepo.save(book);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Book> showAll() {
        List<Book> list = bkRepo.findAll();
        return list;
    }


    public Book showById(Long id) {
        Optional<Book> obj = bkRepo.findById(id);

        if (obj.isPresent()) {
            return obj.get();
        } else {
            return null;
        }
    }

    public Book update(Long id, Book book) {
        Optional<Book> obj = bkRepo.findById(id);

        if (obj.isPresent()) {

            Book bk = obj.get();

            bk.setTitle(book.getTitle());
            bk.setAuthor(book.getAuthor());
            bk.setAvailable(book.getAvailable());
            bk.setBookCategory(book.getBookCategory());

            Book updt = bkRepo.save(bk);
            return updt;
        }

        return null;
    }

    public void delete(Long id) {
        Optional<Book> obj = bkRepo.findById(id);

        if (obj.isPresent()) {
            bkRepo.deleteById(id);
        } else {
            return;
        }
    }

    public List<Book> getByCategory(String categoryName) {
        List<Book> list = bkRepo.findByBookCategoryCategoryName(categoryName);
        return list;
    }

    public List<Book> getByTitle(String title) {
        List<Book> list = bkRepo.findByTitle(title);
        return list;
    }
}
