package com.example.movie_poster.controller.everyone;

import com.example.movie_poster.category.CategoryService;
import com.example.movie_poster.category.dto.CategoryMapper;
import com.example.movie_poster.category.dto.CategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public List<CategoryResponseDto> findAll() {
        return categoryMapper.toResponse(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public CategoryResponseDto findById(@PathVariable int id) {
        return categoryMapper.toResponse(categoryService.findById(id));
    }
}
