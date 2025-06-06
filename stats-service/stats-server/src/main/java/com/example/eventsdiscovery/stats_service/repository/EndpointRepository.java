package com.example.eventsdiscovery.stats_service.repository;

import com.example.eventsdiscovery.stats_service.model.Endpoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EndpointRepository extends JpaRepository<Endpoint, Integer> {
    Optional<Endpoint> findByAppAndUri(String app, String uri);
}
