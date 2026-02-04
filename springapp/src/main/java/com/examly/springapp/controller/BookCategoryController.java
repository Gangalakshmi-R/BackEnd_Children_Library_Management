package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.model.BookCategory;
import com.examly.springapp.service.BookCategoryServiceImpl;

@RestController
@RequestMapping("/api/book-categories")
public class BookCategoryController {

    @Autowired
    private BookCategoryServiceImpl bkserv;

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public ResponseEntity<BookCategory> create(@RequestBody BookCategory bkCat) {
        return new ResponseEntity<>(bkserv.create(bkCat), HttpStatus.CREATED);
    }

     @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping
    public ResponseEntity<List<BookCategory>> showAll() {
        return new ResponseEntity<>(bkserv.showAll(), HttpStatus.OK);
    }

     @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/{id}")
    public ResponseEntity<BookCategory> getById(@PathVariable Long id) {
        return new ResponseEntity<>(bkserv.showById(id), HttpStatus.OK);
    }

     @PreAuthorize("hasRole('LIBRARIAN')")
    @PutMapping("/{id}")
    public ResponseEntity<BookCategory> update(
            @PathVariable Long id,
            @RequestBody BookCategory category) {
        return new ResponseEntity<>(bkserv.update(id, category), HttpStatus.OK);
    }

     @PreAuthorize("hasRole('LIBRARIAN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bkserv.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

     @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/page/{pageNo}/{pageSize}")
    public ResponseEntity<Page<BookCategory>> pages(
            @PathVariable int pageNo,
            @PathVariable int pageSize) {
        return new ResponseEntity<>(bkserv.pages(pageNo, pageSize), HttpStatus.OK);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/sort/{field}")
    public ResponseEntity<List<BookCategory>> sortByField(@PathVariable String field) {
        return new ResponseEntity<>(bkserv.sortByField(field), HttpStatus.OK);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/filter/{field}/{value}")
    public ResponseEntity<List<BookCategory>> filterByField(
            @PathVariable String field,
            @PathVariable String value) {
        return new ResponseEntity<>(bkserv.filterByField(field, value), HttpStatus.OK);
    }
}
