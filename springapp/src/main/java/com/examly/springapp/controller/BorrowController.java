package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.model.Borrow;
import com.examly.springapp.service.BorrowService;

@RestController
@RequestMapping("/api/borrows")
public class BorrowController {

    @Autowired
    private BorrowService brserv;

    @PostMapping
    public ResponseEntity<Borrow> create(@RequestBody Borrow borrow) {

        Borrow obj = brserv.create(borrow);

        if (obj == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(obj, HttpStatus.CREATED);
        }
    }

    @GetMapping
    public ResponseEntity<List<Borrow>> showAll() {

        List<Borrow> list = brserv.showAll();

        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Borrow> showById(@PathVariable Long id) {

        Borrow obj = brserv.showById(id);

        if (obj == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Borrow> update(
            @PathVariable Long id,
            @RequestBody Borrow borrow) {

        Borrow updated = brserv.update(id, borrow);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        brserv.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
