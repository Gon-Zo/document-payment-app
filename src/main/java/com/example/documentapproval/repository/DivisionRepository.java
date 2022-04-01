package com.example.documentapproval.repository;

import com.example.documentapproval.domain.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DivisionRepository extends JpaRepository<Division, String> {

  <T> List<T> findAllProjectedBy(Class<T> type);
}
