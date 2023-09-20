package com.yachtrent.main.account;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE Account a " +
            "SET a = :updatedAccount " +
            "WHERE a.id = :accountId")
    void updateAccount(
            @Param("accountId") Long accountId,
            @Param("updatedAccount") Account updatedAccount
    );

    Optional<Account> findByName(String name);

    @Modifying
    @Transactional
    @Query("delete from Account a where a.email = :email")
    void deleteByEmail(@Param("email") String email);
}
