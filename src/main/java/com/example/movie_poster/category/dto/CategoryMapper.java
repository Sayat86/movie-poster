package com.example.movie_poster.category.dto;

import com.example.movie_poster.category.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {
    public Category fromCreate(CategoryCreateDto categoryCreate) {
        Category category = new Category();
        category.setName(categoryCreate.getName());
        return category;
    }

    public Category fromUpdate(CategoryUpdateDto categoryUpdate) {
        Category category = new Category();
        category.setName(categoryUpdate.getName());
        return category;
    }

    public CategoryResponseDto toResponse(Category category) {
        CategoryResponseDto categoryResponse = new CategoryResponseDto();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        return categoryResponse;
    }

    public List<CategoryResponseDto> toResponse(List<Category> categories) {
        return categories.stream()
                .map(this::toResponse)
                .toList();
    }

    public void merge(Category existingCategory, Category updateCategory) {
        if (updateCategory.getName() != null && !updateCategory.getName().isBlank()) {
            existingCategory.setName(updateCategory.getName());
        }
    }
}
