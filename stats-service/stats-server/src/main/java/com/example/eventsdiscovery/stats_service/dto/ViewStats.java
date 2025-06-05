package com.example.eventsdiscovery.stats_service.dto;

import lombok.Data;

@Data
public class ViewStats {
    private String app;
    private String uri;
    private Integer hits;
}
