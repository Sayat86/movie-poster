package com.example.eventsdiscovery.controller.admin;

import com.example.eventsdiscovery.compilation.Compilation;
import com.example.eventsdiscovery.compilation.CompilationService;
import com.example.eventsdiscovery.compilation.dto.CompilationCreateDto;
import com.example.eventsdiscovery.compilation.dto.CompilationMapper;
import com.example.eventsdiscovery.compilation.dto.CompilationResponseDto;
import com.example.eventsdiscovery.compilation.dto.CompilationUpdateDto;
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
