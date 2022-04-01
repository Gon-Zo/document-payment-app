package com.example.documentapproval.repository;

import com.example.documentapproval.domain.Document;
import com.example.documentapproval.repository.support.DocumentCustomRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository
    extends JpaRepository<Document, Long>, DocumentCustomRepository {

  @EntityGraph(
      attributePaths = {
        "user",
        "paymentCommentSet",
        "paymentCommentSet.user",
        "paymentCommentSet.document"
      })
  <T> Optional<T> findById(Long id, Class<T> type);

  @EntityGraph(
      attributePaths = {
        "user",
        "paymentCommentSet",
        "paymentCommentSet.user",
        "paymentCommentSet.document"
      })
  <T> List<T> findByUser_Id(Long userId, Class<T> type);
}
