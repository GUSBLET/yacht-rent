package com.yachtrent.databaselayer.repositories;

import com.yachtrent.domain.entities.Account;
import com.yachtrent.domain.entities.Timetable;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {
    @Query("SELECT t\n" +
            "FROM Timetable t\n" +
            "WHERE :startOfRent >= t.startOfRent AND :startOfRent <= t.finishOfRent\n" +
            "      AND :finishOfRent >= :startOfRent AND :finishOfRent <= t.finishOfRent")
    Optional<Timetable> findByTimeRange(@Param("startOfRent") Date startOfRent, @Param("finishOfRent") Date finishOfRent);

}
