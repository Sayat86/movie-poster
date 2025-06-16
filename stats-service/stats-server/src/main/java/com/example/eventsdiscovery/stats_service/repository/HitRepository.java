package com.example.eventsdiscovery.stats_service.repository;

import com.example.eventsdiscovery.stats_service.dto.ViewStats;
import com.example.eventsdiscovery.stats_service.model.Hit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Integer> {
    @Query("""
            select new com.example.eventsdiscovery.stats_service.dto.ViewStats(e.app, e.uri, count(h.ip)) from Endpoint e
            left join e.hits h
            group by e.id""")
    List<ViewStats> findAllViewStats();
}
