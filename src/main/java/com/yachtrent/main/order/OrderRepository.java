package com.yachtrent.main.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.Spliterator;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
            SELECT t
            FROM Order t
            WHERE :startOfRent >= t.startOfRent AND :startOfRent <= t.finishOfRent
                  AND :finishOfRent >= :startOfRent AND :finishOfRent <= t.finishOfRent""")
    Optional<Order> findByTimeRange(Date startOfRent, Date finishOfRent);
}
