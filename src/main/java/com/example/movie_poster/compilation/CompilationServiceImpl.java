package com.example.movie_poster.compilation;

import com.example.movie_poster.compilation.dto.CompilationMapper;
import com.example.movie_poster.exception.ConflictException;
import com.example.movie_poster.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public List<Compilation> findAll() {
        return compilationRepository.findAll();
    }

    @Override
    public Compilation findById(int id) {
        return compilationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Подборка с таким ID не найдена"));
    }

    @Override
    public Compilation create(Compilation compilation) {
        return compilationRepository.save(compilation);
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
        return compilationRepository.save(existingCompilation);
    }
}
