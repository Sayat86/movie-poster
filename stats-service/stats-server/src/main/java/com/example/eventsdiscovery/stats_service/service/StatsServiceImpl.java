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
        return hitRepository.findAllViewStats();
    }
}
