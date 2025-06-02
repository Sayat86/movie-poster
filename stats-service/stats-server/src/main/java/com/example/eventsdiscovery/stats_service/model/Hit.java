package com.example.eventsdiscovery.stats_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "hits")
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String ip;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "endpoint_id")
    private Endpoint endpoint;
}
