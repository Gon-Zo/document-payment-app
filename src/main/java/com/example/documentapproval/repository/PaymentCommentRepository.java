package com.example.documentapproval.repository;

import com.example.documentapproval.domain.PaymentComment;
import com.example.documentapproval.domain.PaymentCommentKey;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentCommentRepository extends JpaRepository<PaymentComment, PaymentCommentKey> {

  @EntityGraph(attributePaths = {"document"})
  List<PaymentComment> findById_DocumentId(Long documentId);
}
