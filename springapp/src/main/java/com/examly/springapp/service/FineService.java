package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.examly.springapp.model.Fine;

public interface FineService {

    Fine create(Fine fine);

    List<Fine> showAll();

    Fine showById(Long id);

    Fine update(Long id, Fine fine);

    void delete(Long id);

    Page<Fine> pagination(int pageNo, int pageSize);

    List<Fine> sortByField(String field);

    List<Fine> filterByField(String field, String value);

}
