package com.example.eventsdiscovery.controller.everyone;

import com.example.eventsdiscovery.category.CategoryService;
import com.example.eventsdiscovery.category.dto.CategoryMapper;
import com.example.eventsdiscovery.category.dto.CategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public List<CategoryResponseDto> findAll(@RequestParam(defaultValue = "0") int from,
                                             @RequestParam(defaultValue = "10") int size) {
        //todo добавить пагинацию
        int page = from / size;
        return categoryMapper.toResponse(categoryService.findAll(page, size));
    }


    @GetMapping("/{id}")
    public CategoryResponseDto findById(@PathVariable int id) {
        return categoryMapper.toResponse(categoryService.findById(id));
    }
}
