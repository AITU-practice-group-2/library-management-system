package com.practice.librarysystem.category;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        logger.info("Request received to create category with name: {}", categoryDTO.getName());
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        logger.info("Category created successfully with id: {}", createdCategory.getCategoryId());
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        logger.info("Request received to get all categories");
        List<CategoryDTO> categories = categoryService.getAllCategories();
        logger.info("Returning {} categories", categories.size());
        return ResponseEntity.ok(categories);
    }

    // similar fix in delete and update methods if you log id:
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id) {
        logger.info("Request received to delete category with id: {}", id);
        categoryService.deleteCategory(id);
        logger.info("Category with id {} deleted successfully", id);
        return ResponseEntity.ok("Category with id " + id + " has been deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        logger.info("Request received to update category with id: {}", id);
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        logger.info("Category updated successfully: {}", updatedCategory);
        return ResponseEntity.ok(updatedCategory);
    }
}