package com.example.movie_poster.controller.admin;

import com.example.movie_poster.compilation.Compilation;
import com.example.movie_poster.compilation.CompilationService;
import com.example.movie_poster.compilation.dto.CompilationCreateDto;
import com.example.movie_poster.compilation.dto.CompilationMapper;
import com.example.movie_poster.compilation.dto.CompilationResponseDto;
import com.example.movie_poster.compilation.dto.CompilationUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationController {
    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationResponseDto create(@Valid @RequestBody CompilationCreateDto compilationCreate) {
        Compilation compilation = compilationMapper.fromCreate(compilationCreate);
        return compilationMapper.toResponse(compilationService.create(compilation));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        compilationService.deleteById(id);
    }

    @PatchMapping("/{id}")
    public CompilationResponseDto update(@Valid @RequestBody CompilationUpdateDto compilationUpdate,
                                         @PathVariable int id) {
        Compilation compilation = compilationMapper.fromUpdate(compilationUpdate);
        return compilationMapper.toResponse(compilationService.update(compilation, id));
    }
}
