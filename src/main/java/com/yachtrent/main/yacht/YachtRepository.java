package com.yachtrent.main.yacht;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YachtRepository extends JpaRepository<Yacht, Long> {
    Optional<Yacht> findByName(String name);
}
