package io.example.documentapproval.repository;

import io.example.documentapproval.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByIdIn(Set<Long> userIds);

    @EntityGraph(attributePaths = {"authoritySet"})
    Optional<User> findByEmail(String email);

    <T> List<T> findAllProjectedBy(Class<T> type);

}
