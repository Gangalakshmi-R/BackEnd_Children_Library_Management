package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.exception.EmptyDataException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Member;
import com.examly.springapp.repository.MemberRepo;
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepo memRepo;

    public Member create(Member member) {
        return memRepo.save(member);
    }

    public List<Member> showAll() {
        List<Member> list = memRepo.findAll();
        if (list.isEmpty()) {
            throw new EmptyDataException("No members found");
        }
        return list;
    }

    public Member showById(Long id) {
        return memRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Member not found with id: " + id));
    }

    public Member update(Long id, Member member) {
        Member existing = showById(id);

        existing.setName(member.getName());
        existing.setPhone(member.getPhone());
        existing.setEmail(member.getEmail());

        return memRepo.save(existing);
    }

    public void delete(Long id) {
        memRepo.delete(showById(id));
    }

    public List<Member> getByPhone(String phone) {
        List<Member> list = memRepo.findByPhone(phone);
        if (list.isEmpty()) {
            throw new EmptyDataException("No member found with phone: " + phone);
        }
        return list;
    }

    public List<Member> getByEmail(String email) {
        List<Member> list = memRepo.findByEmail(email);
        if (list.isEmpty()) {
            throw new EmptyDataException("No member found with email: " + email);
        }
        return list;
    }
}
