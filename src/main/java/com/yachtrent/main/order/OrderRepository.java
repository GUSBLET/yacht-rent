package com.yachtrent.main.order;

import com.yachtrent.main.yacht.Yacht;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.*;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
            SELECT t
            FROM Order t
            WHERE :startOfRent >= t.startOfRent AND :startOfRent <= t.finishOfRent
                  AND :finishOfRent >= :startOfRent AND :finishOfRent <= t.finishOfRent""")
    Optional<Order> findByTimeRange(Instant startOfRent, Instant finishOfRent);


    @Query("UPDATE Order o SET o = :upratedOrder WHERE o.id = :orderId")
    void updateOrder(@Param("orderId") long orderId,
                            @Param("upratedOrder") Order order);


    @Query("SELECT o FROM Order o JOIN o.yachts y WHERE y.id = :yachtId")
    Set<Order> findOrdersByYachtId(@Param("yachtId") Long yachtId);
}
