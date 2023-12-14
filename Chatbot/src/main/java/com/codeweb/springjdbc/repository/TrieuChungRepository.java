package com.codeweb.springjdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codeweb.springjdbc.model.TrieuChung;

@Repository
public interface TrieuChungRepository extends JpaRepository<TrieuChung, String>{

}
