package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.examly.springapp.exception.EmptyDataException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Fine;
import com.examly.springapp.repository.FineRepo;
@Service
public class FineServiceImpl implements FineService {

    @Autowired
    private FineRepo fineRepo;

    public Fine create(Fine fine) {
        return fineRepo.save(fine);
    }

    public List<Fine> showAll() {
        List<Fine> list = fineRepo.findAll();
        if (list.isEmpty()) {
            throw new EmptyDataException("No fines found");
        }
        return list;
    }

    public Fine showById(Long id) {
        return fineRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Fine not found with id: " + id));
    }

    public Fine update(Long id, Fine fine) {
        Fine existing = showById(id);
        existing.setBorrow(fine.getBorrow());
        existing.setAmount(fine.getAmount());
        return fineRepo.save(existing);
    }

    public void delete(Long id) {
        fineRepo.delete(showById(id));
    }

    public Page<Fine> pagination(int pageNo, int pageSize) {
        return fineRepo.findAll(PageRequest.of(pageNo, pageSize));
    }

    public List<Fine> sortByField(String field) {
        List<Fine> list = fineRepo.findAll(Sort.by(field).ascending());
        if (list.isEmpty()) {
            throw new EmptyDataException("No fine data found");
        }
        return list;
    }

    public List<Fine> filterByField(String field, String value) {
        throw new EmptyDataException("Filtering not supported for Fine");
    }
}
