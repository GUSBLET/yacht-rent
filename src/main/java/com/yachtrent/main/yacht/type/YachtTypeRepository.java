package com.yachtrent.main.yacht.type;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YachtTypeRepository extends JpaRepository<YachtType, Long> {
    Optional<YachtType> findByType(String type);
}
