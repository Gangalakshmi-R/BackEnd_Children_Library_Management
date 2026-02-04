package com.examly.springapp.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Role;
import com.examly.springapp.model.RoleType;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}
