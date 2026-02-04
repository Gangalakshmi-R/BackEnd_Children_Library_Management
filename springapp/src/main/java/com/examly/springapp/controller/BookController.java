package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

import com.examly.springapp.model.Book;
import com.examly.springapp.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bkserv;

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        return new ResponseEntity<>(bkserv.create(book), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('LIBRARIAN','MEMBER')")
    @GetMapping
    public ResponseEntity<List<Book>> showAll() {
        return new ResponseEntity<>(bkserv.showAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('LIBRARIAN','MEMBER')")
    @GetMapping("/{id}")
    public ResponseEntity<Book> showById(@PathVariable Long id) {
        return new ResponseEntity<>(bkserv.showById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PutMapping("/{id}")
    public ResponseEntity<Book> update(
            @PathVariable Long id,
            @RequestBody Book book) {
        return new ResponseEntity<>(bkserv.update(id, book), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bkserv.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('LIBRARIAN','MEMBER')")
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Book>> getByCategory(@PathVariable String categoryName) {
        return new ResponseEntity<>(bkserv.getByCategory(categoryName), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('LIBRARIAN','MEMBER')")
    @GetMapping("/title/{title}")
    public ResponseEntity<List<Book>> getByTitle(@PathVariable String title) {
        return new ResponseEntity<>(bkserv.getByTitle(title), HttpStatus.OK);
    }
}
