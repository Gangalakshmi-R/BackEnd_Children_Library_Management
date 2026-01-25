package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.examly.springapp.model.BookCategory;

public interface BookCategoryService {

    BookCategory create(BookCategory category);

    List<BookCategory> showAll();

    BookCategory showById(Long id);

    BookCategory update(Long id, BookCategory category);

    void delete(Long id);

    // Page<BookCategory> getCategoriesPaginated(int page, int size);
}
