package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
}
