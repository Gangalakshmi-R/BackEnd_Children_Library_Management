package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Member;
import com.examly.springapp.repository.MemberRepo;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepo memRepo;

    @Override
    public Member create(Member member) {
        try {
            return memRepo.save(member);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Member> showAll() {
        List<Member> list = memRepo.findAll();
        return list;
    }

    @Override
    public Member showById(Long id) {

        Optional<Member> obj = memRepo.findById(id);

        if (obj.isPresent()) {
            return obj.get();
        } else {
            return null;
        }
    }

    @Override
    public Member update(Long id, Member member) {

        Optional<Member> obj = memRepo.findById(id);

        if (obj.isPresent()) {

            Member existing = obj.get();

            existing.setName(member.getName());
            existing.setPhone(member.getPhone());
            existing.setEmail(member.getEmail());

            Member updatedMember = memRepo.save(existing);
            return updatedMember;
        }

        return null;
    }

    @Override
    public void delete(Long id) {

        Optional<Member> obj = memRepo.findById(id);

        if (obj.isPresent()) {
            memRepo.deleteById(id);
        } else {
            return;
        }
    }

    @Override
    public List<Member> getByPhone(String phone) {
        List<Member> list = memRepo.findByPhone(phone);
        return list;
    }

    @Override
    public List<Member> getByEmail(String email) {
        List<Member> list = memRepo.findByEmail(email);
        return list;
    }
}
