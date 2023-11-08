package com.yachtrent.main.yacht;

import com.yachtrent.main.account.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface YachtRepository extends JpaRepository<Yacht, Long> {
    Optional<Yacht> findByName(String name);

    Set<Yacht> findByAccount(Account account);

    @Query("SELECT DISTINCT y FROM Yacht y LEFT JOIN FETCH y.orders WHERE y.id = :id")
    Optional<Yacht> findYachtWithOrdersById(@Param("id") Long id);

    @Modifying
    @Transactional
    void deleteById(long id);
}
