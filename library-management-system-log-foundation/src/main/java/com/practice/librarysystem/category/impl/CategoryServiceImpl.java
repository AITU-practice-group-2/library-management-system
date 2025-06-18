package com.practice.librarysystem.category.impl;

import com.practice.librarysystem.category.Category;
import com.practice.librarysystem.category.CategoryDTO;
import com.practice.librarysystem.category.CategoryMapper;

import com.practice.librarysystem.category.CategoryRepository;

import com.practice.librarysystem.category.CategoryService;

import com.practice.librarysystem.exception.DataConflictException;
import com.practice.librarysystem.exception.NotFoundException;
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
        Category category = CategoryMapper.toEntity(categoryDTO);

        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new DataConflictException("Category with this name already exists");
        }

        Category saved = categoryRepository.save(category);
        return CategoryMapper.toDTO(saved);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        return CategoryMapper.toDTO(category);
    }

    @Override
    public CategoryDTO updateCategory(long id, CategoryDTO categoryDTO) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        if (!existing.getName().equals(categoryDTO.getName()) &&
                categoryRepository.findByName(categoryDTO.getName()).isPresent()) {
            throw new DataConflictException("Category with this name already exists");
        }

        existing.setName(categoryDTO.getName());
        existing.setDescription(categoryDTO.getDescription());

        Category saved = categoryRepository.save(existing);
        return CategoryMapper.toDTO(saved);
    }

    @Override
    public void deleteCategory(long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);
    }
}
