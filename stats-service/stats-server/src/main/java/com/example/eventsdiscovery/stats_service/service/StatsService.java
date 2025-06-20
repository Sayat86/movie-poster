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

//    List<ViewStats> findAllViewStatsStartBetweenEnd(LocalDateTime start,
//                                                         LocalDateTime end);
//
//    List<ViewStats> findAllViewStatsStartBetweenEndAndUris(LocalDateTime start,
//                                                           LocalDateTime end,
//                                                           List<String> uris);
//
//    List<ViewStats> findAllViewStatsStartBetweenEndUnique(LocalDateTime start,
//                                                          LocalDateTime end);
//
//    List<ViewStats> findAllViewStatsStartBetweenEndAndUrisUnique(LocalDateTime start,
//                                                                      LocalDateTime end,
//                                                                      List<String> uris);
}
