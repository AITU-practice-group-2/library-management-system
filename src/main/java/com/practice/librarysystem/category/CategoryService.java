    package com.practice.librarysystem.category;

    import java.util.List;

    public interface CategoryService {
        CategoryDTO createCategory(CategoryDTO categoryDTO);

        List<CategoryDTO> getAllCategories();
        CategoryDTO getCategoryById(long id);
        CategoryDTO updateCategory(long id, CategoryDTO categoryDTO);
        void deleteCategory(long id);

    }