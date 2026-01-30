package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Borrow;
import com.examly.springapp.repository.BorrowRepo;

@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BorrowRepo brRepo;

    @Override
    public Borrow create(Borrow borrow) {
        try {
            return brRepo.save(borrow);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Borrow> showAll() {
        List<Borrow> list = brRepo.findAll();
        return list;
    }

    @Override
    public Borrow showById(Long id) {

        Optional<Borrow> obj = brRepo.findById(id);

        if (obj.isPresent()) {
            return obj.get();
        } else {
            return null;
        }
    }

    @Override
    public Borrow update(Long id, Borrow borrow) {

        Optional<Borrow> obj = brRepo.findById(id);

        if (obj.isPresent()) {

            Borrow existing = obj.get();

            existing.setBook(borrow.getBook());
            existing.setChild(borrow.getChild());
            existing.setBorrowDate(borrow.getBorrowDate());
            existing.setReturnDate(borrow.getReturnDate());

            Borrow updatedBorrow = brRepo.save(existing);
            return updatedBorrow;
        }

        return null;
    }

    public void delete(Long id) {

        Optional<Borrow> obj = brRepo.findById(id);

        if (obj.isPresent()) {
            brRepo.deleteById(id);
        } else {
            return;
        }
    }

    public Page<Borrow> pagination(int pageNo, int pageSize) {
//check pageable
        PageRequest pg = PageRequest.of(pageNo,pageSize);

        return brRepo.findAll(pg);
    }

    public List<Borrow> sortByField(String field) {

        return brRepo.findAll(Sort.by(field).ascending());
    }

    public List<Borrow> filterByField(String field, String value) {

        List<Borrow> result = new java.util.ArrayList<>();
        List<Borrow> list = brRepo.findAll();

        if (field == null || value == null) {
            return result;
        }

        switch (field.toLowerCase()) {

            case "borrowdate":
                for (Borrow b : list) {
                    if (b.getBorrowDate() != null &&
                            b.getBorrowDate().toString().equals(value)) {
                        result.add(b);
                    }
                }
                return result;

            case "returndate":
                for (Borrow b : list) {
                    if (b.getReturnDate() != null &&
                            b.getReturnDate().toString().equals(value)) {
                        result.add(b);
                    }
                }
                return result;

            default:
                return result;
        }
    }

}
