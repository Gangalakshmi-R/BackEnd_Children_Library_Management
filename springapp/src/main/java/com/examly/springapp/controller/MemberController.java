package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.model.Member;
import com.examly.springapp.service.MemberService;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memserv;
 @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public ResponseEntity<Member> create(@RequestBody Member member) {
        return new ResponseEntity<>(memserv.create(member), HttpStatus.CREATED);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping
    public ResponseEntity<List<Member>> showAll() {
        return new ResponseEntity<>(memserv.showAll(), HttpStatus.OK);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/{id}")
    public ResponseEntity<Member> showById(@PathVariable Long id) {
        return new ResponseEntity<>(memserv.showById(id), HttpStatus.OK);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @PutMapping("/{id}")
    public ResponseEntity<Member> update(
            @PathVariable Long id,
            @RequestBody Member member) {
        return new ResponseEntity<>(memserv.update(id, member), HttpStatus.OK);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memserv.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/phone/{phone}")
    public ResponseEntity<List<Member>> getByPhone(@PathVariable String phone) {
        return new ResponseEntity<>(memserv.getByPhone(phone), HttpStatus.OK);
    }
 @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/email/{email}")
    public ResponseEntity<List<Member>> getByEmail(@PathVariable String email) {
        return new ResponseEntity<>(memserv.getByEmail(email), HttpStatus.OK);
    }
}
