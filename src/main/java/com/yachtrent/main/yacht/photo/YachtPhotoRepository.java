package com.yachtrent.main.yacht.photo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface YachtPhotoRepository extends JpaRepository<YachtPhoto, Long> {

    @Modifying
    @Query("DELETE FROM YachtPhoto p WHERE p.yacht.id = :yachtId")
    void deleteAllByYachtId(@Param("yachtId") Long yachtId);

}
