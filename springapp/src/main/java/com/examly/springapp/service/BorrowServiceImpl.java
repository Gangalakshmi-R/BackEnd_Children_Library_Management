package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.examly.springapp.exception.EmptyDataException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Borrow;
import com.examly.springapp.repository.BorrowRepo;
@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BorrowRepo brRepo;

    public Borrow create(Borrow borrow) {
        return brRepo.save(borrow);
    }

    public List<Borrow> showAll() {
        List<Borrow> list = brRepo.findAll();
        if (list.isEmpty()) {
            throw new EmptyDataException("No borrow records found");
        }
        return list;
    }

    public Borrow showById(Long id) {
        return brRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Borrow not found with id: " + id));
    }

    public Borrow update(Long id, Borrow borrow) {
        Borrow existing = showById(id);

        existing.setBook(borrow.getBook());
        existing.setChild(borrow.getChild());
        existing.setBorrowDate(borrow.getBorrowDate());
        existing.setReturnDate(borrow.getReturnDate());

        return brRepo.save(existing);
    }

    public void delete(Long id) {
        brRepo.delete(showById(id));
    }

    public Page<Borrow> pagination(int pageNo, int pageSize) {
        return brRepo.findAll(PageRequest.of(pageNo, pageSize));
    }

    public List<Borrow> sortByField(String field) {
        List<Borrow> list = brRepo.findAll(Sort.by(field).ascending());
        if (list.isEmpty()) {
            throw new EmptyDataException("No borrow data found");
        }
        return list;
    }

    public List<Borrow> filterByField(String field, String value) {

        List<Borrow> result = new java.util.ArrayList<>();
        List<Borrow> list = brRepo.findAll();

        if (list.isEmpty()) {
            throw new EmptyDataException("No borrow data available");
        }

        switch (field.toLowerCase()) {

            case "borrowdate":
                for (Borrow b : list) {
                    if (b.getBorrowDate() != null &&
                            b.getBorrowDate().toString().equals(value)) {
                        result.add(b);
                    }
                }
                break;

            case "returndate":
                for (Borrow b : list) {
                    if (b.getReturnDate() != null &&
                            b.getReturnDate().toString().equals(value)) {
                        result.add(b);
                    }
                }
                break;
        }

        if (result.isEmpty()) {
            throw new EmptyDataException("No matching borrow records found");
        }

        return result;
    }
}
