package com.yachtrent.main.rent.time;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface RentTimetableRepository extends JpaRepository<RentTimetable, Long> {

    @Query("""
            SELECT t
            FROM RentTimetable t
            WHERE :startOfRent >= t.startOfRent AND :startOfRent <= t.finishOfRent
                  AND :finishOfRent >= :startOfRent AND :finishOfRent <= t.finishOfRent""")
    Optional<RentTimetable> findByTimeRange(Date startOfRent, Date finishOfRent);
}
