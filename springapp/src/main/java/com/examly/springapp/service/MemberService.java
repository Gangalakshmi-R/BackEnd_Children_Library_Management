package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.Member;

public interface MemberService {

    Member create(Member member);

    List<Member> showAll();

    Member showById(Long id);

    Member update(Long id, Member member);

    void delete(Long id);

    List<Member> getByPhone(String phone);

    List<Member> getByEmail(String email);
}
