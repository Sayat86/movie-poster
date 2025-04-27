package com.example.movie_poster.event;

import com.example.movie_poster.category.Category;
import com.example.movie_poster.category.CategoryRepository;
import com.example.movie_poster.event.dto.EventCreateDto;
import com.example.movie_poster.exception.BadRequestException;
import com.example.movie_poster.exception.NotFoundException;
import com.example.movie_poster.user.User;
import com.example.movie_poster.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Event create(Event event, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким ID не найден"));
        Category category = categoryRepository.findById(event.getCategory().getId())
                .orElseThrow(() -> new NotFoundException("Категория с таким ID не найдена"));
        event.setCategory(category);
        event.setInitiator(user);
        return eventRepository.save(event);
    }
}
