package com.example.eventsdiscovery.controller.admin;

import com.example.eventsdiscovery.category.Category;
import com.example.eventsdiscovery.category.CategoryService;
import com.example.eventsdiscovery.category.dto.CategoryCreateDto;
import com.example.eventsdiscovery.category.dto.CategoryMapper;
import com.example.eventsdiscovery.category.dto.CategoryResponseDto;
import com.example.eventsdiscovery.category.dto.CategoryUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto create(@Valid @RequestBody CategoryCreateDto categoryCreate) {
        Category category = categoryMapper.fromCreate(categoryCreate);
        return categoryMapper.toResponse(categoryService.create(category));
    }

    @PatchMapping("/{id}")
    public CategoryResponseDto update(@Valid @RequestBody CategoryUpdateDto categoryUpdate, @PathVariable int id) {
        Category category = categoryMapper.fromUpdate(categoryUpdate);
        return categoryMapper.toResponse(categoryService.update(category, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        categoryService.deleteById(id);
    }
}
