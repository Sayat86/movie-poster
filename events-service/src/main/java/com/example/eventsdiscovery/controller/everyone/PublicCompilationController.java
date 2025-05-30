package com.example.eventsdiscovery.controller.everyone;

import com.example.eventsdiscovery.compilation.CompilationService;
import com.example.eventsdiscovery.compilation.dto.CompilationMapper;
import com.example.eventsdiscovery.compilation.dto.CompilationResponseDto;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
@Validated
public class PublicCompilationController {
    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;

    @GetMapping
    public List<CompilationResponseDto> findAll(@RequestParam(required = false) Boolean pinned,
                                                @RequestParam(defaultValue = "0") @Min(0) int from,
                                                @RequestParam(defaultValue = "10") @Min(1) int size) {
        int page = from / size;
        return compilationMapper.toResponse(compilationService.findAll(pinned, page, size));
        // todo добавить пагинацию
    }

    @GetMapping("/{id}")
    public CompilationResponseDto findById(@PathVariable int id) {
        return compilationMapper.toResponse(compilationService.findById(id));
    }
}
