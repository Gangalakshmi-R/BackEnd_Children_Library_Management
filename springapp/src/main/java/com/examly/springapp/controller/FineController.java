package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.model.Fine;
import com.examly.springapp.service.FineService;

@RestController
@RequestMapping("/api/fines")
public class FineController {

    @Autowired
    private FineService fineserv;

    @PostMapping
    public ResponseEntity<Fine> create(@RequestBody Fine fine) {

        Fine obj = fineserv.create(fine);

        if (obj == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(obj, HttpStatus.CREATED);
        }
    }

    @GetMapping
    public ResponseEntity<List<Fine>> showAll() {

        List<Fine> list = fineserv.showAll();

        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fine> showById(@PathVariable Long id) {

        Fine fine = fineserv.showById(id);

        if (fine == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(fine, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fine> update(
            @PathVariable Long id,
            @RequestBody Fine fine) {

        Fine updated = fineserv.update(id, fine);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        fineserv.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
