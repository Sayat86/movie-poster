package com.example.eventsdiscovery.stats_service.service;

import com.example.eventsdiscovery.stats_service.dto.EndpointHit;
import com.example.eventsdiscovery.stats_service.dto.ViewStats;


import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void create(EndpointHit endpointHit);
    List<ViewStats> findAll(LocalDateTime start,
                            LocalDateTime end,
                            List<String> uris,
                            Boolean unique);
}
