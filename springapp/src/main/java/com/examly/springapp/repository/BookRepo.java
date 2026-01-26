package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Book;

@Repository
public interface BookRepo extends JpaRepository<Book,Long>{
    
    List<Book> findByTitle(String title);

    List<Book> findByBookCategoryCategoryName(String categoryName);
}
