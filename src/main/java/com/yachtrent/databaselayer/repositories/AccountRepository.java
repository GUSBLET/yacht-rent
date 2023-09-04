package com.yachtrent.databaselayer.repositories;

import com.yachtrent.domain.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.login = :login OR a.email = :email")
    Optional<Account> findByEmailOrLogin(@Param("login") String login, @Param("email") String email);
    Optional<Account> findByLogin(String login);
}
