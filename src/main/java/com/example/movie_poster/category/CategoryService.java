package com.example.movie_poster.category;

import java.util.List;

public interface CategoryService {
    Category create(Category category);
    Category update(Category category, int id);
    Category findById(int id);
    void deleteById(int id);
    List<Category> findAll(int page, int size);
}
