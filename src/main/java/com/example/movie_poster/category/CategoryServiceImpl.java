package com.example.movie_poster.category;

import com.example.movie_poster.category.dto.CategoryMapper;
import com.example.movie_poster.exception.ConflictException;
import com.example.movie_poster.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category updateCategory, int id) {
        Optional<Category> optionalName = categoryRepository.findByName(updateCategory.getName());

        if (optionalName.isPresent()) {
            Category foundCategoryByName = optionalName.get();
            if (foundCategoryByName.getId() != id) {
                throw new ConflictException("Категория с таким именем уже существует");
            }
        }
        Category existingCategory = findById(id);
        categoryMapper.merge(existingCategory, updateCategory);
        return categoryRepository.save(existingCategory);
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория с таким ID не найдена"));
    }

    @Override
    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
