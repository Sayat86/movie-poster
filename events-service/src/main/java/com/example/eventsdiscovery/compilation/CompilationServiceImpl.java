package com.example.eventsdiscovery.compilation;

import com.example.eventsdiscovery.compilation.dto.CompilationMapper;
import com.example.eventsdiscovery.event.Event;
import com.example.eventsdiscovery.event.EventRepository;
import com.example.eventsdiscovery.exception.ConflictException;
import com.example.eventsdiscovery.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventRepository eventRepository;

    @Override
    public List<Compilation> findAll(Boolean pinned, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (pinned == null) {
            return compilationRepository.findAll(pageable).getContent(); // все записи
        }

        return compilationRepository.findByPinned(pinned, pageable).getContent();
    }

    @Override
    public Compilation findById(int id) {
        return compilationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Подборка с таким ID не найдена"));
    }

    @Override
    public Compilation create(Compilation compilation) {

        if (compilation.getEvents() != null) {
            List<Event> events = eventRepository.findAllById(compilation.getEvents().stream().map(Event::getId).toList());
            compilationRepository.save(compilation);
            compilation.setEvents(events);
        } else {
            compilationRepository.save(compilation);
        }
        return compilation;
    }

    @Override
    public void deleteById(int id) {
        compilationRepository.deleteById(id);
    }

    @Override
    public Compilation update(Compilation updateCompilation, int id) {
        Optional<Compilation> optionalName = compilationRepository.findByTitle(updateCompilation.getTitle());

        if (optionalName.isPresent()) {
            Compilation foundCompilationByTitle = optionalName.get();
            if (foundCompilationByTitle.getId() != id) {
                throw new ConflictException("Подборка с таким заголовком уже существует");
            }
        }
        Compilation existingCompilation = findById(id);
        compilationMapper.merge(existingCompilation, updateCompilation);
        // todo: переделать может быть дубли событий
        existingCompilation.getEvents().addAll(updateCompilation.getEvents());

        return compilationRepository.save(existingCompilation);
    }
}
