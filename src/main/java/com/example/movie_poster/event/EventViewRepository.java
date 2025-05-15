package com.example.movie_poster.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventViewRepository extends JpaRepository<EventView, Long> {
    boolean existsByEventAndIpAddress(Event event, String ipAddress);
}
