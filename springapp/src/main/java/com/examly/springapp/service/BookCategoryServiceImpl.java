package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.BookCategory;
import com.examly.springapp.repository.BookCategoryRepo;

@Service
public class BookCategoryServiceImpl implements BookCategoryService {

    @Autowired
    private BookCategoryRepo bkCatRep;

    public BookCategory create(BookCategory bkcat) {
        try {
            return bkCatRep.save(bkcat);
        } catch (Exception e) {
            return null;
        }
    }

    public List<BookCategory> showAll() {
        List<BookCategory> list = bkCatRep.findAll();
        if (list.isEmpty()) {
            return list;
        } else {
            return list;
        }
    }

    public BookCategory showById(Long id) {
        Optional<BookCategory> obj = bkCatRep.findById(id);
        if (obj.isPresent()) {
            return obj.get();
        } else {
            return null;
        }
    }

    public BookCategory update(Long id, BookCategory category) {
        Optional<BookCategory> obj = bkCatRep.findById(id);
        if (obj.isPresent()) {
            BookCategory cat = obj.get();
            cat.setCategoryName(category.getCategoryName());
            BookCategory updatedCategory = bkCatRep.save(cat);
            return updatedCategory;
        }
        return null;
    }

    public void delete(Long id) {
        Optional<BookCategory> obj = bkCatRep.findById(id);
        if (obj.isPresent()) {
            bkCatRep.deleteById(id);
        } else {
            return;
        }
    }

    public Page<BookCategory> pages(int pgNo, int pgSize) {
        Pageable pg = PageRequest.of(pgNo, pgSize);
        return bkCatRep.findAll(pg);

    }

    public List<BookCategory> sortByField(String field) {

        return bkCatRep.findAll(
                org.springframework.data.domain.Sort.by(field).ascending());
    }

    
public List<BookCategory> filterByField(String field, String value) {

    List<BookCategory> result = new java.util.ArrayList<>();
    List<BookCategory> list = bkCatRep.findAll();

    if (field == null || value == null) {
        return result;
    }

    switch (field.toLowerCase()) {

        case "categoryname":
            for (BookCategory c : list) {
                if (c.getCategoryName() != null &&
                        c.getCategoryName().equals(value)) {
                    result.add(c);
                }
            }
            return result;

        default:
            return result;
    }
}


}
