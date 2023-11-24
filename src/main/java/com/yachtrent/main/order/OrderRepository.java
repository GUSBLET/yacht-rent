package com.yachtrent.main.order;

import com.yachtrent.main.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("FROM Order o WHERE o.date BETWEEN :startDate AND :endDate")
    List<Order> findByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<Order> findByAccount(Account account);
}
