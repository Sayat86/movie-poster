package com.example.movie_poster.compilation.dto;

import com.example.movie_poster.category.Category;
import com.example.movie_poster.category.dto.CategoryResponseDto;
import com.example.movie_poster.compilation.Compilation;
import com.example.movie_poster.event.Event;
import com.example.movie_poster.event.dto.EventShortDto;
import com.example.movie_poster.user.User;
import com.example.movie_poster.user.dto.UserResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompilationMapper {
    public Compilation fromCreate(CompilationCreateDto compilationCreate) {
        Compilation compilation = new Compilation();
        compilation.setTitle(compilationCreate.getTitle());
        compilation.setPinned(compilationCreate.getPinned());

        if (compilationCreate.getEvents() != null) {
            List<Event> events = new ArrayList<>();
            for (Integer eventId : compilationCreate.getEvents()) {
                Event event = new Event();
                event.setId(eventId);
                events.add(event);
            }
            compilation.setEvents(events);
        }
        return compilation;
    }

    public Compilation fromUpdate(CompilationUpdateDto compilationUpdate) {
        Compilation compilation = new Compilation();
        compilation.setTitle(compilationUpdate.getTitle());
        compilation.setPinned(compilationUpdate.getPinned());
        if (compilationUpdate.getEvents() != null) {
            List<Event> events = new ArrayList<>();
            for (Integer eventId : compilationUpdate.getEvents()) {
                Event event = new Event();
                event.setId(eventId);
                events.add(event);
            }
            compilation.setEvents(events);
        }
        return compilation;
    }

    public CompilationResponseDto toResponse(Compilation compilation) {
        CompilationResponseDto compilationResponse = new CompilationResponseDto();
        compilationResponse.setId(compilation.getId());
        compilationResponse.setTitle(compilation.getTitle());
        compilationResponse.setPinned(compilation.getPinned());
        // создание списка для того чтобы передать setEvents
        List<EventShortDto> events = new ArrayList<>();

        // перебор каждого события подборки
        for (Event event : compilation.getEvents()) {
            // конвертация Event в EventShortDto и добавление в список
            events.add(toShortDto(event));
        }
        // передача списка событий в CompilationResponseDto
        compilationResponse.setEvents(events);
        return compilationResponse;
    }

    public List<CompilationResponseDto> toResponse(List<Compilation> compilations) {
        return compilations.stream()
                .map(this::toResponse)
                .toList();
    }

    public void merge(Compilation existingCompilation, Compilation updateCompilation) {
        if (updateCompilation.getTitle() != null && !updateCompilation.getTitle().isBlank()) {
            existingCompilation.setTitle(updateCompilation.getTitle());
        }
        if (updateCompilation.getPinned() != null) {
            existingCompilation.setPinned(updateCompilation.getPinned());
        }
    }

    private EventShortDto toShortDto(Event event) {
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
