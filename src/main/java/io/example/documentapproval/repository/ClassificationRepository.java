package io.example.documentapproval.repository;

import io.example.documentapproval.domain.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassificationRepository extends JpaRepository<Classification, Long> {

    <T> List<T> findAllProjectedBy(Class<T> type);

}
