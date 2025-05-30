package com.example.eventsdiscovery.request;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    boolean existsByRequesterIdAndEventId(int requesterId, int eventId);

    Integer countByEventIdAndStatus(int eventId, RequestState status);

    List<Request> findAllByEventId(int eventId);

    List<Request> findByRequesterId(int userId);

    List<Request> findByEventIdAndStatus(Integer eventId, RequestState status);
}
