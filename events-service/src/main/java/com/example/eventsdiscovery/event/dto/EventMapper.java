package com.example.eventsdiscovery.event.dto;

import com.example.eventsdiscovery.category.Category;
import com.example.eventsdiscovery.category.dto.CategoryResponseDto;
import com.example.eventsdiscovery.event.Event;
import com.example.eventsdiscovery.event.EventState;
import com.example.eventsdiscovery.event.EventStateActionAdmin;
import com.example.eventsdiscovery.event.EventStateActionUser;
import com.example.eventsdiscovery.user.User;
import com.example.eventsdiscovery.user.dto.UserResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        eventFullDto.setCategory(toResponse(event.getCategory()));
        eventFullDto.setInitiator(toResponse(event.getInitiator()));
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
        eventShortDto.setCategory(toResponse(event.getCategory()));
        eventShortDto.setInitiator(toResponse(event.getInitiator()));
        return eventShortDto;
    }

    public Event fromUserUpdate(UpdateEventUserRequest updateEvent) {
        Event event = new Event();
        event.setAnnotation(updateEvent.getAnnotation());
        Category category = new Category();
        category.setId(updateEvent.getCategory());
        event.setCategory(category);
        event.setDescription(updateEvent.getDescription());
        event.setEventDate(updateEvent.getEventDate());
        event.setLocation(updateEvent.getLocation());
        event.setPaid(updateEvent.getPaid());
        event.setParticipantLimit(updateEvent.getParticipantLimit());
        event.setRequestModeration(updateEvent.getRequestModeration());
        if (updateEvent.getStateAction() == EventStateActionUser.CANCEL_REVIEW) {
            event.setState(EventState.CANCELED);
        } else {
            event.setState(EventState.PENDING);
        }
        event.setTitle(updateEvent.getTitle());
        return event;
    }

    public Event fromAdminUpdate(UpdateEventAdminRequest updateEventAdmin) {
        Event event = new Event();
        event.setAnnotation(updateEventAdmin.getAnnotation());
        Category category = new Category();
        category.setId(updateEventAdmin.getCategory());
        event.setCategory(category);
        event.setDescription(updateEventAdmin.getDescription());
        event.setEventDate(updateEventAdmin.getEventDate());
        event.setLocation(updateEventAdmin.getLocation());
        event.setPaid(updateEventAdmin.getPaid());
        event.setParticipantLimit(updateEventAdmin.getParticipantLimit());
        event.setRequestModeration(updateEventAdmin.getRequestModeration());
        event.setTitle(updateEventAdmin.getTitle());
        if (updateEventAdmin.getStateAction() == EventStateActionAdmin.PUBLISH_EVENT) {
            event.setState(EventState.PUBLISHED);
        } else {
            event.setState(EventState.CANCELED);
        }
        return event;
    }

    public List<EventShortDto> toShortDto(List<Event> events) {
        return events.stream()
                .map(this::toShortDto)
                .collect(Collectors.toList());
    }

    public List<EventFullDto> toFullDto(List<Event> events) {
        return events.stream()
                .map(this::toFullDto)
                .collect(Collectors.toList());
    }

    private CategoryResponseDto toResponse(Category category) {
        CategoryResponseDto categoryResponse = new CategoryResponseDto();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        return categoryResponse;
    }

    private UserResponseDto toResponse(User user) {
        UserResponseDto userResponse = new UserResponseDto();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }
}
