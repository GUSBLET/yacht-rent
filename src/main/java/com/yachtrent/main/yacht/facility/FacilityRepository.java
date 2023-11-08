package com.yachtrent.main.yacht.facility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    @Query("SELECT f FROM Facility f LEFT JOIN f.yacht y WHERE y.id = :id")
    List<Facility> findAllByYachtId(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM Facility f WHERE f.yacht.id = :yachtId")
    void deleteAllByYachtId(@Param("yachtId") Long yachtId);

    @Query("select f from Facility f where f.name = :name and f.count = :count and f.yacht.id = :yachtId")
    Optional<Facility> findByNameAndCountAndYachtId(@Param("name") String  name,@Param("count") short count,@Param("yachtId") long yachtId);
}
