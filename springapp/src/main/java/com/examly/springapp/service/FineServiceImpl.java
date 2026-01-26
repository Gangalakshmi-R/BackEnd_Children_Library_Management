package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Fine;
import com.examly.springapp.repository.FineRepo;

@Service
public class FineServiceImpl implements FineService {

    @Autowired
    private FineRepo fineRepo;

    public Fine create(Fine fine) {
        try {
            return fineRepo.save(fine);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Fine> showAll() {
        List<Fine> list = fineRepo.findAll();
        return list;
    }

    public Fine showById(Long id) {

        Optional<Fine> obj = fineRepo.findById(id);

        if (obj.isPresent()) {
            return obj.get();
        } else {
            return null;
        }
    }

    public Fine update(Long id, Fine fine) {

        Optional<Fine> obj = fineRepo.findById(id);

        if (obj.isPresent()) {

            Fine existing = obj.get();

            existing.setBorrow(fine.getBorrow());
            existing.setAmount(fine.getAmount());

            Fine updatedFine = fineRepo.save(existing);
            return updatedFine;
        }

        return null;
    }

    public void delete(Long id) {

        Optional<Fine> obj = fineRepo.findById(id);

        if (obj.isPresent()) {
            fineRepo.deleteById(id);
        } else {
            return;
        }
    }
}
