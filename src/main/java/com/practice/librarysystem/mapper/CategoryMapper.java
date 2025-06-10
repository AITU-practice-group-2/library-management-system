package com.practice.librarysystem.mapper;

import com.practice.librarysystem.dto.CategoryDTO;
import com.practice.librarysystem.entity.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryDTO dto) {
        return Category.builder()
                .categoryId(dto.getCategoryId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public static CategoryDTO toDTO(Category entity) {
        return CategoryDTO.builder()
                .categoryId(entity.getCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
