package com.yachtrent.databaselayer.repositories;


import com.yachtrent.domain.entities.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {
    @Query("""
            SELECT t
            FROM Timetable t
            WHERE :startOfRent >= t.startOfRent AND :startOfRent <= t.finishOfRent
                  AND :finishOfRent >= :startOfRent AND :finishOfRent <= t.finishOfRent""")
    Optional<Timetable> findByTimeRange(@Param("startOfRent") Date startOfRent, @Param("finishOfRent") Date finishOfRent);

}
