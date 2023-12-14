package com.codeweb.springjdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codeweb.springjdbc.model.VanDe;

@Repository
public interface VanDeRepository extends JpaRepository<VanDe,String> {
}
