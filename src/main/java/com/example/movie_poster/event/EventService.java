package com.example.movie_poster.event;

import java.util.List;

public interface EventService {
    List<Event> findEventAddedByUserId(int userId, int page, int size);
    Event create(Event event, int userId);
    Event findFullEventInfoByUser(int userId, int eventId);
    Event updateByUser(Event event, int userId, int eventId);
}
