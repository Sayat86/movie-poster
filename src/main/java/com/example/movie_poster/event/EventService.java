package com.example.movie_poster.event;

import java.util.List;

public interface EventService {
    List<Event> findEventAddedByUserId(int userId, int page, int size);
    Event create(Event event, int userId);
    Event findFullEventInfoByUser(int userId, int eventId);
    Event updateByUser(Event event, int userId, int eventId);
    Event findById(int id);
    List<Event> findAll(String text, List<Integer> categories, boolean paid, String rangeStart, String rangeEnd,
                        boolean onlyAvailable, String sort, int from, int size);
}
