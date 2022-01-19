package io.example.documentapproval.repository;

import io.example.documentapproval.domain.Document;
import io.example.documentapproval.enums.StateCode;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @EntityGraph(attributePaths = {"classification", "user", "state"})
    <T> List<T> findByUser_IdAndState_Code(Long userId, StateCode stateCode, Class<T> type);

    @EntityGraph(attributePaths = {"classification", "user", "state"})
    <T> List<T> findByUser_IdAndState_CodeIn(Long userId, List<StateCode> stateCode, Class<T> type);

    @Override
    @EntityGraph(attributePaths = {"classification", "user", "state", "step", "paymentUsers"})
    Optional<Document> findById(Long aLong);

    @EntityGraph(attributePaths = {"classification", "user", "state", "step", "paymentUsers"})
    <T> Optional<T> findById(Long aLong, Class<T> type);

}
