package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Member;

@Repository
public interface MemberRepo extends JpaRepository<Member, Long> {

    List<Member> findByPhone(String phone);

    List<Member> findByEmail(String email);
}
