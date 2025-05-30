package com.example.eventsdiscovery.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event>{
    Page<Event> findByInitiatorId(Integer userId, Pageable pageable);
    Optional<Event> findByIdAndInitiatorId(int eventId, int initiatorId);
    boolean existsByCategoryId(int categoryId);

}
