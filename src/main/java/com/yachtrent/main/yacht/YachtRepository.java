package com.yachtrent.main.yacht;

import com.yachtrent.main.account.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface YachtRepository extends JpaRepository<Yacht, Long>, JpaSpecificationExecutor<Yacht> {
    Optional<Yacht> findByName(String name);

    @Query("FROM Yacht y WHERE UPPER(y.name) LIKE CONCAT('%', UPPER(:name), '%')")
    List<Yacht> findYachtsByName(@Param("name") String name);

    List<Yacht> findAll(Specification<Yacht> specification);

    Set<Yacht> findByAccount(Account account);

    @Modifying
    @Transactional
    void deleteById(long id);
}
