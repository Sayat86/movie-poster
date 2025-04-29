package com.example.movie_poster.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    Page<Event> findByInitiatorId(Integer userId, Pageable pageable);
}
