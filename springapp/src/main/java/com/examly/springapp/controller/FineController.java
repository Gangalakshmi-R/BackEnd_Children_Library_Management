package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.model.Fine;
import com.examly.springapp.service.FineService;

@RestController
@RequestMapping("/api/fines")
public class FineController {

    @Autowired
    private FineService fineserv;
 @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public ResponseEntity<Fine> create(@RequestBody Fine fine) {
        return new ResponseEntity<>(fineserv.create(fine), HttpStatus.CREATED);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping
    public ResponseEntity<List<Fine>> showAll() {
        return new ResponseEntity<>(fineserv.showAll(), HttpStatus.OK);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/{id}")
    public ResponseEntity<Fine> showById(@PathVariable Long id) {
        return new ResponseEntity<>(fineserv.showById(id), HttpStatus.OK);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @PutMapping("/{id}")
    public ResponseEntity<Fine> update(
            @PathVariable Long id,
            @RequestBody Fine fine) {
        return new ResponseEntity<>(fineserv.update(id, fine), HttpStatus.OK);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fineserv.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/page/{pageNo}/{pageSize}")
    public ResponseEntity<Page<Fine>> pages(
            @PathVariable int pageNo,
            @PathVariable int pageSize) {
        return new ResponseEntity<>(fineserv.pagination(pageNo, pageSize), HttpStatus.OK);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/sort/{field}")
    public ResponseEntity<List<Fine>> sortByField(@PathVariable String field) {
        return new ResponseEntity<>(fineserv.sortByField(field), HttpStatus.OK);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/filter/{field}/{value}")
    public ResponseEntity<List<Fine>> filterByField(
            @PathVariable String field,
            @PathVariable String value) {
        return new ResponseEntity<>(fineserv.filterByField(field, value), HttpStatus.OK);
    }
}
