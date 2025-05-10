package com.example.movie_poster.request;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    boolean existsByRequesterIdAndEventId(int requesterId, int eventId);

    Integer countByEventIdAndStatus(int eventId, RequestState status);
}
