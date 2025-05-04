package com.example.movie_poster.controller.everyone;

import com.example.movie_poster.compilation.CompilationService;
import com.example.movie_poster.compilation.dto.CompilationCreateDto;
import com.example.movie_poster.compilation.dto.CompilationMapper;
import com.example.movie_poster.compilation.dto.CompilationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationController {
    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;

    @GetMapping
    public List<CompilationResponseDto> findAll() {
        return compilationMapper.toResponse(compilationService.findAll());
    }

    @GetMapping("/{id}")
    public CompilationResponseDto findById(@PathVariable int id) {
        return compilationMapper.toResponse(compilationService.findById(id));
    }
}
