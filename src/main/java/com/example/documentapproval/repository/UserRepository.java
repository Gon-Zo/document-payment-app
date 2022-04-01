package com.example.documentapproval.repository;

import com.example.documentapproval.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  <T> Optional<T> findByEmail(String email, Class<T> type);

  boolean existsByEmail(String email);
}
