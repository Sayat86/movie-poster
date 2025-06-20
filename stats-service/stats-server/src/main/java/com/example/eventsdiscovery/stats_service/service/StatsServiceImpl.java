package com.example.eventsdiscovery.stats_service.service;

import com.example.eventsdiscovery.stats_service.dto.EndpointHit;
import com.example.eventsdiscovery.stats_service.dto.ViewStats;
import com.example.eventsdiscovery.stats_service.model.Endpoint;
import com.example.eventsdiscovery.stats_service.model.Hit;
import com.example.eventsdiscovery.stats_service.repository.EndpointRepository;
import com.example.eventsdiscovery.stats_service.repository.HitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final EndpointRepository endpointRepository;
    private final HitRepository hitRepository;

    @Override
    public void create(EndpointHit endpointHit) {
        Hit hit = new Hit();
        hit.setIp(endpointHit.getIp());
        hit.setTimestamp(endpointHit.getTimestamp());
        Optional<Endpoint> optional = endpointRepository
                .findByAppAndUri(endpointHit.getApp(), endpointHit.getUri());
        if (optional.isEmpty()) {
            Endpoint endpoint = new Endpoint();
            endpoint.setApp(endpointHit.getApp());
            endpoint.setUri(endpointHit.getUri());
            endpointRepository.save(endpoint);
            hit.setEndpoint(endpoint);
        } else {
            Endpoint endpoint = optional.get();
            hit.setEndpoint(endpoint);
        }
        hitRepository.save(hit);
    }

    @Override
    public List<ViewStats> findAll(LocalDateTime start, LocalDateTime end,
                                   List<String> uris, Boolean unique) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        if (unique != null && unique) {
            if (uris != null && !uris.isEmpty()) {
                return hitRepository.findAllViewStatsWhereStartBetweenEndAndUrisUnique(start, end, uris);
            } else {
                return hitRepository.findAllViewStatsWhereStartBetweenEndUnique(start, end);
            }
        } else {
            if (uris != null && !uris.isEmpty()) {
                return hitRepository.findAllViewStatsWhereStartBetweenEndAndUris(start, end, uris);
            } else {
                return hitRepository.findAllViewStatsWhereStartBetweenEnd(start, end);
            }
        }
    }

//    @Override
//    public List<ViewStats> findAllViewStatsStartBetweenEnd(LocalDateTime start,
//                                                           LocalDateTime end) {
//        return hitRepository.findAllViewStatsWhereStartBetweenEnd(start, end);
//    }
//
//    @Override
//    public List<ViewStats> findAllViewStatsStartBetweenEndAndUris(LocalDateTime start,
//                                                                LocalDateTime end,
//                                                                List<String> uris) {
//        return hitRepository.findAllViewStatsWhereStartBetweenEndAndUris(start, end, uris);
//    }
//
//    @Override
//    public List<ViewStats> findAllViewStatsStartBetweenEndUnique(LocalDateTime start,
//                                                                 LocalDateTime end) {
//        return hitRepository.findAllViewStatsWhereStartBetweenEndUnique(start, end);
//    }
//
//    @Override
//    public List<ViewStats> findAllViewStatsStartBetweenEndAndUrisUnique(LocalDateTime start,
//                                                                        LocalDateTime end,
//                                                                        List<String> uris) {
//        return hitRepository.findAllViewStatsWhereStartBetweenEndAndUrisUnique(start, end, uris);
//    }
}
