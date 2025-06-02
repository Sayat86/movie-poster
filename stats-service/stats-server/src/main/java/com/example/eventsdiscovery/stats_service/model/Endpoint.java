package com.example.eventsdiscovery.stats_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "endpoints")
public class Endpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String app;
    private String uri;

    @OneToMany(mappedBy = "endpoint")
    private List<Hit> hits;
}
