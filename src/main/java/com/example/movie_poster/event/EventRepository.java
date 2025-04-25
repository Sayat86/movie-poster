package com.example.movie_poster.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.awt.print.Pageable;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByInitiatorId(Integer userId, Pageable pageable);
}
