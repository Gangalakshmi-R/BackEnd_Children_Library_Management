package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.model.Member;
import com.examly.springapp.service.MemberService;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memserv;

    @PostMapping
    public ResponseEntity<Member> create(@RequestBody Member member) {

        Member obj = memserv.create(member);

        if (obj == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(obj, HttpStatus.CREATED);
        }
    }

    @GetMapping
    public ResponseEntity<List<Member>> showAll() {

        List<Member> list = memserv.showAll();

        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> showById(@PathVariable Long id) {

        Member member = memserv.showById(id);

        if (member == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> update(
            @PathVariable Long id,
            @RequestBody Member member) {

        Member updated = memserv.update(id, member);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        memserv.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<?> getByPhone(@PathVariable String phone) {

        List<Member> list = memserv.getByPhone(phone);

        if (list.isEmpty()) {
            return new ResponseEntity<>("No member found with phone: " + phone, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<Member>> getByEmail(@PathVariable String email) {

        List<Member> list = memserv.getByEmail(email);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
