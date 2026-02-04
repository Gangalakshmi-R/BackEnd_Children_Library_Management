package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.examly.springapp.exception.EmptyDataException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.BookCategory;
import com.examly.springapp.repository.BookCategoryRepo;

@Service
public class BookCategoryServiceImpl implements BookCategoryService {

    @Autowired
    private BookCategoryRepo bkCatRep;

    public BookCategory create(BookCategory bkcat) {
        return bkCatRep.save(bkcat);
    }

    public List<BookCategory> showAll() {
        List<BookCategory> list = bkCatRep.findAll();
        if (list.isEmpty()) {
            throw new EmptyDataException("No book categories found");
        }
        return list;
    }

    public BookCategory showById(Long id) {
        return bkCatRep.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("BookCategory not found with id: " + id));
    }

    public BookCategory update(Long id, BookCategory category) {
        BookCategory existing = showById(id);
        existing.setCategoryName(category.getCategoryName());
        return bkCatRep.save(existing);
    }

    public void delete(Long id) {
        BookCategory existing = showById(id);
        bkCatRep.delete(existing);
    }

    public Page<BookCategory> pages(int pgNo, int pgSize) {
        Pageable pg = PageRequest.of(pgNo, pgSize);
        return bkCatRep.findAll(pg);
    }

    public List<BookCategory> sortByField(String field) {
        List<BookCategory> list =
                bkCatRep.findAll(Sort.by(field).ascending());
        if (list.isEmpty()) {
            throw new EmptyDataException("No categories found for sorting");
        }
        return list;
    }

    public List<BookCategory> filterByField(String field, String value) {

        List<BookCategory> result = new java.util.ArrayList<>();
        List<BookCategory> list = bkCatRep.findAll();

        if (list.isEmpty()) {
            throw new EmptyDataException("No book categories available");
        }

        switch (field.toLowerCase()) {

            case "categoryname":
                for (BookCategory c : list) {
                    if (c.getCategoryName() != null &&
                            c.getCategoryName().equals(value)) {
                        result.add(c);
                    }
                }
                break;

            default:
                break;
        }

        if (result.isEmpty()) {
            throw new EmptyDataException("No matching categories found");
        }

        return result;
    }
}
