package com.example.movie_poster.compilation.dto;

import com.example.movie_poster.compilation.Compilation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompilationMapper {
    public Compilation fromCreate(CompilationCreateDto compilationCreate) {
        Compilation compilation = new Compilation();
        compilation.setTitle(compilationCreate.getTitle());
        compilation.setPinned(compilationCreate.getPinned());
      //  compilation.setCompilationEvents(); todo
        return compilation;
    }

    public CompilationResponseDto toResponse(Compilation compilation) {
        CompilationResponseDto compilationResponse = new CompilationResponseDto();
        compilationResponse.setId(compilation.getId());
        compilationResponse.setTitle(compilation.getTitle());
        compilationResponse.setPinned(compilation.getPinned());
      //  compilationResponse.setEvents(); todo
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
}
