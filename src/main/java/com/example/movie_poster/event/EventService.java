package com.example.movie_poster.event;

import java.util.List;

public interface EventService {
    Event create(Event event, int userId);
    List<Event> findEventAddedByUserId(int userId, int page, int size);
}
