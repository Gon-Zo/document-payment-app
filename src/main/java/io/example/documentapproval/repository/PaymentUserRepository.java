package io.example.documentapproval.repository;

import io.example.documentapproval.domain.PaymentUser;
import io.example.documentapproval.enums.StateCode;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentUserRepository extends JpaRepository<PaymentUser, Long> {

    @EntityGraph(attributePaths = {"document", "document.state", "user"})
    <T> List<T> findByUser_IdAndDocument_State_CodeIn(Long userId, List<StateCode> codes, Class<T> type);

    @EntityGraph(attributePaths = {"document", "document.state", "user"})
    <T> List<T> findByUser_IdAndDocument_State_Code(Long userId, StateCode code, Class<T> type);

    Optional<PaymentUser> findByUser_IdAndDocument_Id(Long userId, Long documentId);

    @EntityGraph(attributePaths = {"document", "user", "paymentComment"})
    List<PaymentUser> findByDocument_Id(Long documentId);

    @EntityGraph(attributePaths = {"document"})
    @Query("SELECT pu from PaymentUser pu join PaymentStep ps on (ps.document = pu.document and ps.step = pu.step) where pu.user.id = :userId and pu.document.state.code = :code")
    <T> List<T> selectInBoxDocumentList(@Param("userId") Long userId, @Param("code") StateCode code, Class<T> type);

    @EntityGraph(attributePaths = {"document", "document.state", "user"})
    @Query("SELECT pu from PaymentUser pu where (pu.user.id = :userId or pu.document.user.id = :userId) and pu.document.state.code in (:codes)")
    <T> List<T> selectArchiveDocumentList(@Param("userId") Long userId, @Param("codes") List<StateCode> codes, Class<T> type);

}
