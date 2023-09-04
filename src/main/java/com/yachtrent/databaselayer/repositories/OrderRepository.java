package com.yachtrent.databaselayer.repositories;

import com.yachtrent.domain.entities.Account;
import com.yachtrent.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
