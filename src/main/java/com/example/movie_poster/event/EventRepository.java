package com.example.movie_poster.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    Page<Event> findByInitiatorId(Integer userId, Pageable pageable);
    Optional<Event> findByIdAndInitiatorId(int eventId, int initiatorId);
    boolean existsByCategoryId(int categoryId);

    @Query("SELECT e FROM Event e " +
            "WHERE (:text IS NULL OR LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%')) " +
            "   OR LOWER(e.description) LIKE LOWER(CONCAT('%', :text, '%'))) " +
            "AND (:categories IS NULL OR e.category.id IN :categories) " +
            "AND (:paid IS NULL OR e.paid = :paid) " +
            "AND (:rangeStart IS NULL OR e.eventDate >= :rangeStart) " +
            "AND (:rangeEnd IS NULL OR e.eventDate <= :rangeEnd) " +
            "AND e.state = com.example.movie_poster.event.EventState.PUBLISHED")
    Page<Event> findAllWithFilters(@Param("text") String text,
                                   @Param("categories") List<Integer> categories,
                                   @Param("paid") Boolean paid,
                                   @Param("rangeStart") LocalDateTime rangeStart,
                                   @Param("rangeEnd") LocalDateTime rangeEnd,
                                   Pageable pageable);
}
