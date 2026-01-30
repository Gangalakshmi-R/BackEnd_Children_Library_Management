package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.model.Book;
import com.examly.springapp.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bkserv;

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {

        Book obj = bkserv.create(book);

        if (obj == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(obj, HttpStatus.CREATED);
        }
    }

    @GetMapping
    public ResponseEntity<List<Book>> showAll() {

        List<Book> list = bkserv.showAll();

        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> showById(@PathVariable Long id) {

        Book book = bkserv.showById(id);

        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(
            @PathVariable Long id,
            @RequestBody Book book) {

        Book updated = bkserv.update(id, book);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        bkserv.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Book>> getByCategory(@PathVariable String categoryName) {

        List<Book> list = bkserv.getByCategory(categoryName);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<?> getByTitle(@PathVariable String title) {

        List<Book> list = bkserv.getByTitle(title);

        if (list.isEmpty()) {
            return new ResponseEntity<>("No book found with title: " + title, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/page/{pageNo}/{pageSize}")
    public ResponseEntity<Page<Book>> pages(@PathVariable int pageNo, @PathVariable int pageSize) {
        Page<Book> pg = bkserv.pagination(pageNo, pageSize);
        return new ResponseEntity<>(pg, HttpStatus.OK);
    }

    @GetMapping("/page/{pageNo}/{pageSize}/{field}")
    public ResponseEntity<Page<Book>> pageswithfield(@PathVariable int pageNo, @PathVariable int pageSize,
            @PathVariable String field) {
        Page<Book> pg = bkserv.pageswithfield(pageNo, pageSize, field);
        return new ResponseEntity<>(pg, HttpStatus.OK);
    }

    @GetMapping("/filter/{field}/{value}")
    public ResponseEntity<List<Book>> filterByField(
            @PathVariable String field,
            @PathVariable String value) {

        List<Book> list = bkserv.filterByField(field, value);

        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
