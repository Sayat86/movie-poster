package com.example.eventsdiscovery.stats_service.repository;

import com.example.eventsdiscovery.stats_service.model.Hit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HitRepository extends JpaRepository<Hit, Integer> {
}
