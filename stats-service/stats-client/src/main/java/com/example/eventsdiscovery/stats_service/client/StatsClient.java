package com.example.eventsdiscovery.stats_service.client;

import com.example.eventsdiscovery.stats_service.dto.ViewStats;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class StatsClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:9090";
    public List<ViewStats> findAll(LocalDateTime start,
                                   LocalDateTime end,
                                   List<String> uris,
                                   Boolean unique) {
        // "/events/1,/events/2"
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> params = Map.of(
                "start", formatter.format(start),
                "end", formatter.format(end),
                "uris", String.join(",", uris),
                "unique", unique
        );
        String url = baseUrl + "/stats?start={start}&end={end}&uris={uris}&unique={unique}";

        ViewStats[] arr = restTemplate.getForObject(url, ViewStats[].class, params);
//        List<ViewStats> list = new ArrayList<>();
//        for (ViewStats stats : arr) {
//            list.add(stats);
//        }
//        return list;

        return Arrays.asList(arr);
    }
}
