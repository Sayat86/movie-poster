package com.example.movie_poster.event.dto;

import com.example.movie_poster.category.Category;
import com.example.movie_poster.event.Event;
import com.example.movie_poster.event.EventState;
import com.example.movie_poster.user.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class EventMapper {
    //EventCreateDto -> Event
    public Event fromCreate(EventCreateDto eventCreate) {
        Event event = new Event();
        Category category = new Category();
        category.setId(eventCreate.getCategory());
        event.setCategory(category);
        event.setEventDate(eventCreate.getEventDate());
        event.setAnnotation(eventCreate.getAnnotation());
        event.setPaid(eventCreate.getPaid());
        event.setDescription(eventCreate.getDescription());
        event.setRequestModeration(eventCreate.getRequestModeration());
        event.setLocation(eventCreate.getLocation());
        event.setTitle(eventCreate.getTitle());
        event.setParticipantLimit(eventCreate.getParticipantLimit());
        return event;
    }

    //Event -> EventFullDto toFullDto()
    public EventFullDto toFullDto(Event event) {
        return null;
    }
    //Event -> EventShortDto toShortDto()


}
