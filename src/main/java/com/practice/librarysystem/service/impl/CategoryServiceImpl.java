package com.practice.librarysystem.service.impl;
import com.practice.librarysystem.exception.AlreadyExistsException;

import com.practice.librarysystem.dto.CategoryDTO;
import com.practice.librarysystem.entity.Category;
import com.practice.librarysystem.mapper.CategoryMapper;
import com.practice.librarysystem.repository.CategoryRepository;
import com.practice.librarysystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        // Convert DTO to Entity
        Category category = CategoryMapper.toEntity(categoryDTO);

        // Check if category with the same name already exists in the database
        if(categoryRepository.findByName(category.getName()).isPresent()) {
            throw new AlreadyExistsException("Category with this name already exists");
        }

        // Save the new category to the database
        Category saved = categoryRepository.save(category);

        // Convert saved entity back to DTO and return
        return CategoryMapper.toDTO(saved);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
