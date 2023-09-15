package com.yachtrent.main.yacht;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

//TODO переделать
public interface YachtRepository extends JpaRepository<Yacht, Long> {
    @Query("SELECT  y FROM Yacht y JOIN FETCH y.harbors JOIN FETCH y.orders " +
            "JOIN FETCH y.facilities " +
            "JOIN FETCH y.reviews " +
            "JOIN FETCH y.account " +
            "JOIN FETCH y.yachtType " +
            "JOIN FETCH y.creator where y.account.id = :accountId")
    List<Yacht> findAllYachtsWithRelationshipsByAccountId(@Param("accountId") long id);

    Optional<Yacht> findByName(String name);
}
