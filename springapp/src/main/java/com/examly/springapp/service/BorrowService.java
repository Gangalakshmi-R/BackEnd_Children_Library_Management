package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.Borrow;

public interface BorrowService {

    Borrow create(Borrow borrow);

    List<Borrow> showAll();

    Borrow showById(Long id);

    Borrow update(Long id, Borrow borrow);

    void delete(Long id);
}
