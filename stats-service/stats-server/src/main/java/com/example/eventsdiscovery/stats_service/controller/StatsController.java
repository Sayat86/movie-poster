package com.example.eventsdiscovery.stats_service.controller;

import com.example.eventsdiscovery.stats_service.dto.EndpointHit;
import com.example.eventsdiscovery.stats_service.dto.ViewStats;
import com.example.eventsdiscovery.stats_service.service.StatsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsController {
    private final StatsService service;
    @GetMapping("/stats")
    public List<ViewStats> findAll(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                   @RequestParam(required = false) List<String> uris,
                                   @RequestParam(required = false) Boolean unique) {
        return service.findAll(start, end, uris, unique);

        // start=2025-01-01 00:00:00
        // start=2025-12-31 00:00:00
        // uris=GET /events/3,GET /events/99
        // unique=true
    }

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody EndpointHit endpointHit) {
        service.create(endpointHit);
    }
}
