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

    
@GetMapping("/page/{pageNo}/{pageSize}")
public ResponseEntity<org.springframework.data.domain.Page<Borrow>> pages(
        @PathVariable int pageNo,
        @PathVariable int pageSize) {

    org.springframework.data.domain.Page<Borrow> pg = brserv.pagination(pageNo, pageSize);
    return new ResponseEntity<>(pg, HttpStatus.OK);
}


@GetMapping("/sort/{field}")
public ResponseEntity<List<Borrow>> sortByField(@PathVariable String field) {

    List<Borrow> list = brserv.sortByField(field);

    if (list.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(list, HttpStatus.OK);
}


@GetMapping("/filter/{field}/{value}")
public ResponseEntity<List<Borrow>> filterByField(
        @PathVariable String field,
        @PathVariable String value) {

    List<Borrow> list = brserv.filterByField(field, value);

    if (list.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(list, HttpStatus.OK);
}

}
