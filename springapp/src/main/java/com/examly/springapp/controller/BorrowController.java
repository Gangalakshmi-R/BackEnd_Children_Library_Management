package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.model.Borrow;
import com.examly.springapp.service.BorrowService;

@RestController
@RequestMapping("/api/borrows")
public class BorrowController {

    @Autowired
    private BorrowService brserv;
 @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public ResponseEntity<Borrow> create(@RequestBody Borrow borrow) {
        return new ResponseEntity<>(brserv.create(borrow), HttpStatus.CREATED);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping
    public ResponseEntity<List<Borrow>> showAll() {
        return new ResponseEntity<>(brserv.showAll(), HttpStatus.OK);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/{id}")
    public ResponseEntity<Borrow> showById(@PathVariable Long id) {
        return new ResponseEntity<>(brserv.showById(id), HttpStatus.OK);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @PutMapping("/{id}")
    public ResponseEntity<Borrow> update(
            @PathVariable Long id,
            @RequestBody Borrow borrow) {
        return new ResponseEntity<>(brserv.update(id, borrow), HttpStatus.OK);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        brserv.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/page/{pageNo}/{pageSize}")
    public ResponseEntity<Page<Borrow>> pages(
            @PathVariable int pageNo,
            @PathVariable int pageSize) {
        return new ResponseEntity<>(brserv.pagination(pageNo, pageSize), HttpStatus.OK);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/sort/{field}")
    public ResponseEntity<List<Borrow>> sortByField(@PathVariable String field) {
        return new ResponseEntity<>(brserv.sortByField(field), HttpStatus.OK);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/filter/{field}/{value}")
    public ResponseEntity<List<Borrow>> filterByField(
            @PathVariable String field,
            @PathVariable String value) {
        return new ResponseEntity<>(brserv.filterByField(field, value), HttpStatus.OK);
    }
}
