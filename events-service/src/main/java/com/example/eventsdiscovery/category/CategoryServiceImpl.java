package com.example.eventsdiscovery.category;

import com.example.eventsdiscovery.category.dto.CategoryMapper;
import com.example.eventsdiscovery.event.EventRepository;
import com.example.eventsdiscovery.exception.ConflictException;
import com.example.eventsdiscovery.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final EventRepository eventRepository;

    @Override
    public Category create(Category category) {
        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());

        if (existingCategory.isPresent()) {
            throw new ConflictException("Категория с таким именем уже существует");
        }

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
    @Transactional
    public void deleteById(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория с таким ID не найдена"));

        boolean hasEvents = eventRepository.existsByCategoryId(id);
        if (hasEvents) {
            throw new ConflictException("Нельзя удалить категорию, к которой привязаны события");
        }

        categoryRepository.deleteById(id);
    }


    @Override
    public List<Category> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> pageResult = categoryRepository.findAll(pageable);
        return pageResult.getContent();
    }
}
