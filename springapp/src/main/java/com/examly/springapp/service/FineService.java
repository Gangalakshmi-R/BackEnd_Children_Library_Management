package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.Fine;

public interface FineService {

    Fine create(Fine fine);

    List<Fine> showAll();

    Fine showById(Long id);

    Fine update(Long id, Fine fine);

    void delete(Long id);
}
