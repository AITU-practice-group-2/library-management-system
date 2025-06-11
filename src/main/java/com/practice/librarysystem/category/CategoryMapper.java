package com.practice.librarysystem.category;

public class CategoryMapper {

    public static Category toEntity(CategoryDTO dto) {
        return Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public static CategoryDTO toDTO(Category entity) {
        return CategoryDTO.builder()
                .categoryId(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
