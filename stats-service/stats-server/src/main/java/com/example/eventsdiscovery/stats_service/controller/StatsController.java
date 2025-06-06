package com.example.eventsdiscovery.stats_service.controller;

import com.example.eventsdiscovery.stats_service.dto.EndpointHit;
import com.example.eventsdiscovery.stats_service.dto.ViewStats;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsController {
    @GetMapping("/stats")
    public List<ViewStats> findAll(@RequestParam LocalDateTime start,
                                   @RequestParam LocalDateTime end,
                                   @RequestParam(required = false) List<String> uris,
                                   @RequestParam(required = false) Boolean unique) {
        return null;
    }

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody EndpointHit endpointHit) {

    }
}
