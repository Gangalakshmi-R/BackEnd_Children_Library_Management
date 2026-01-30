package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.examly.springapp.model.Borrow;

public interface BorrowService {

    Borrow create(Borrow borrow);

    List<Borrow> showAll();

    Borrow showById(Long id);

    Borrow update(Long id, Borrow borrow);

    void delete(Long id);

    Page<Borrow> pagination(int pageNo, int pageSize);

    List<Borrow> sortByField(String field);

    List<Borrow> filterByField(String field, String value);

}
