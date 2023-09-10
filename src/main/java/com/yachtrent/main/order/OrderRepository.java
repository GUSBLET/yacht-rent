package com.yachtrent.main.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Spliterator;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
