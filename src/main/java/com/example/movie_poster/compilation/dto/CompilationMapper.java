package com.example.movie_poster.compilation.dto;

import com.example.movie_poster.compilation.Compilation;
import org.springframework.stereotype.Component;

@Component
public class CompilationMapper {
    public Compilation fromCreate(CompilationCreateDto compilationCreate) {
        Compilation compilation = new Compilation();
        compilation.setTitle(compilationCreate.getTitle());
        compilation.setPinned(compilationCreate.getPinned());
        compilation.setCompilationEvents();
        return compilation;
    }
}
