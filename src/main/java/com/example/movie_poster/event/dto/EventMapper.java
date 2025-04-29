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
        event.setAnnotation(eventCreate.getAnnotation());
        event.setDescription(eventCreate.getDescription());
        event.setEventDate(eventCreate.getEventDate());
        event.setLocation(eventCreate.getLocation());
        event.setPaid(eventCreate.getPaid());
        event.setParticipantLimit(eventCreate.getParticipantLimit());
        event.setRequestModeration(eventCreate.getRequestModeration());
        event.setTitle(eventCreate.getTitle());
        return event;
    }

    //Event -> EventFullDto toFullDto()
    public EventFullDto toFullDto(Event event) {
        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setId(event.getId());
        eventFullDto.setTitle(event.getTitle());
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setEventDate(event.getEventDate());
        eventFullDto.setCreatedOn(event.getCreatedOn());
        eventFullDto.setPublishedOn(event.getPublishedOn());
        eventFullDto.setLocation(event.getLocation());
        eventFullDto.setPaid(event.getPaid());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        eventFullDto.setRequestModeration(event.getRequestModeration());
        eventFullDto.setState(event.getState());
        eventFullDto.setConfirmedRequests(event.getConfirmedRequests());
        eventFullDto.setViews(event.getViews());
        //CategoryResponseDto category
        //UserResponseDto initiator
        return eventFullDto;
    }
    //Event -> EventShortDto toShortDto()
    public EventShortDto toShortDto(Event event) {
        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setId(event.getId());
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setEventDate(event.getEventDate());
        eventShortDto.setPaid(event.getPaid());
        eventShortDto.setConfirmedRequests(event.getConfirmedRequests());
        eventShortDto.setViews(event.getViews());
        return eventShortDto;
    }


}
