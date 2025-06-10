    package com.practice.librarysystem.service.impl;
    import com.practice.librarysystem.exception.AlreadyExistsException;

    import com.practice.librarysystem.dto.CategoryDTO;
    import com.practice.librarysystem.entity.Category;
    import com.practice.librarysystem.mapper.CategoryMapper;
    import com.practice.librarysystem.repository.CategoryRepository;
    import com.practice.librarysystem.service.CategoryService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.data.crossstore.ChangeSetPersister;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Service;
    import org.springframework.web.bind.annotation.PathVariable;

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


        @Override
        public CategoryDTO getCategoryById(long id) {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("Category not found"));
            // SYNTAX ERROR: should be `() -> new RuntimeException(...)`
            return CategoryMapper.toDTO(category);

        }

        @Override
        public CategoryDTO updateCategory(long id, CategoryDTO categoryDTO) {
            Category existing = categoryRepository.findById(id)
                    .orElseThrow(()->new RuntimeException("Category not found"));

            if (!existing.getName().equals(categoryDTO.getName())  &&
                categoryRepository.findByName(categoryDTO.getName()).isPresent()) {
        throw new AlreadyExistsException("Category with this name already exists");

            }
            existing.setDescription(categoryDTO.getDescription());  // update description
            existing.setName(categoryDTO.getName());                // update name
            Category saved = categoryRepository.save(existing);
            return CategoryMapper.toDTO(saved);

        }

        @Override
        public void deleteCategory(long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(id);

        }

    }