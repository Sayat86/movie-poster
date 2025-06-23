package com.example.eventsdiscovery.event;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<Event> findEventAddedByUserId(int userId, int page, int size);
    Event create(Event event, int userId);
    Event findFullEventInfoByUser(int userId, int eventId);
    Event updateByUser(Event event, int userId, int eventId);
    Event updateByAdmin(int eventId, Event event);
    Event findById(int id, HttpServletRequest request);
    List<Event> findAllPublic(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                              LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, int from, int size);
    List<Event> findAllAdmin(List<Integer> users, List<EventState> states, List<Integer> categories,
                             LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);
}
