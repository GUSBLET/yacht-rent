package com.yachtrent.main.account;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    @Query(""" 
            SELECT DISTINCT a FROM  Account a LEFT JOIN FETCH a.roles r WHERE NOT r.id = 3
            """)
    List<Account> findUsersByRoles();

    @Query("""
            SELECT DISTINCT a FROM  Account a LEFT JOIN FETCH a.roles r WHERE NOT r.id IS NOT NULL
            OR UPPER(a.name) LIKE CONCAT('%', UPPER(:name), '%')
            OR UPPER(a.lastName) LIKE CONCAT('%', UPPER(:name), '%')
            """)
    List<Account> findAccountsByName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a = :updatedAccount WHERE a.id = :accountId")
    void updateAccount(@Param("accountId") Long accountId, @Param("updatedAccount") Account updatedAccount);

    @Modifying
    @Transactional
    @Query("DELETE FROM Account a WHERE a.email = :email")
    void deleteByEmail(@Param("email") String email);
}
