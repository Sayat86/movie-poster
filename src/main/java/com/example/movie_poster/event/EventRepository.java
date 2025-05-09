package com.example.movie_poster.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    Page<Event> findByInitiatorId(Integer userId, Pageable pageable);
    Optional<Event> findByIdAndInitiatorId(int eventId, int initiatorId);
    boolean existsByCategoryId(int categoryId);
}
