package com.example.eventsdiscovery.stats_service.repository;

import com.example.eventsdiscovery.stats_service.dto.ViewStats;
import com.example.eventsdiscovery.stats_service.model.Hit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Integer> {
    @Query("""
            select new com.example.eventsdiscovery.stats_service.dto.ViewStats(e.app, e.uri, count(h.ip)) from Endpoint e
            left join e.hits h
            group by e.id""")
    List<ViewStats> findAllViewStats();

    @Query("""
    select new com.example.eventsdiscovery.stats_service.dto.ViewStats(e.app, e.uri, count(h.ip))
    from Endpoint e
    left join e.hits h on h.timestamp between :start and :end
    group by e.app, e.uri""")
    List<ViewStats> findAllViewStatsWhereStartBetweenEnd(@Param("start") LocalDateTime start,
                                                         @Param("end") LocalDateTime end);

    @Query("""
    select new com.example.eventsdiscovery.stats_service.dto.ViewStats(e.app, e.uri, count(h.ip))
    from Endpoint e
    left join e.hits h on h.timestamp between :start and :end
    where e.uri in :uris
    group by e.app, e.uri""")
    List<ViewStats> findAllViewStatsWhereStartBetweenEndAndUris(@Param("start") LocalDateTime start,
                                                                @Param("end") LocalDateTime end,
                                                                @Param("uris") List<String> uris);

    @Query("""
        select new com.example.eventsdiscovery.stats_service.dto.ViewStats(e.app, e.uri, count(distinct h.ip))
        from Endpoint e
        left join e.hits h on h.timestamp between :start and :end
        group by e.app, e.uri""")
    List<ViewStats> findAllViewStatsWhereStartBetweenEndUnique(@Param("start") LocalDateTime start,
                                                                @Param("end") LocalDateTime end);

    @Query("""
    select new com.example.eventsdiscovery.stats_service.dto.ViewStats(e.app, e.uri, count(distinct h.ip))
    from Endpoint e
    left join e.hits h on h.timestamp between :start and :end
    where e.uri in :uris
    group by e.app, e.uri
""")
    List<ViewStats> findAllViewStatsWhereStartBetweenEndAndUrisUnique(@Param("start") LocalDateTime start,
                                                                      @Param("end") LocalDateTime end,
                                                                      @Param("uris") List<String> uris);
}
