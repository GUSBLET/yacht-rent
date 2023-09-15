package com.yachtrent.main.yacht.creator;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreatorRepository extends JpaRepository<Creator, Long> {
    Optional<Creator> findByСompanyName(String companyName);
}
