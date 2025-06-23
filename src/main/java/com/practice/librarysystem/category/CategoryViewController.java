package com.practice.librarysystem.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories/view")
public class CategoryViewController {

    private final CategoryService categoryService;

    // Show list of categories + Add form
    @GetMapping("/view")
    public String showCategoryList(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("newCategory", new CategoryDTO());
        return "categories";  // loads categories.html
    }

    // Handle create category form submit
    @PostMapping
    public String createCategory(@ModelAttribute("newCategory") CategoryDTO newCategory,
                                 RedirectAttributes redirectAttributes) {
        categoryService.createCategory(newCategory);
        redirectAttributes.addFlashAttribute("success", "Category created successfully!");
        return "redirect:/categories/view/view";  // Or wherever you want to redirect after creation
    }


    // Handle delete category form submit
    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryService.deleteCategory(id);
        redirectAttributes.addFlashAttribute("success", "Category deleted successfully!");
        return "redirect:/categories/view/view";
    }

    // Show edit form page (optional)
    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model) {
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);
        model.addAttribute("category", categoryDTO);
        return "edit-category"; // Optional separate Thymeleaf template for editing
    }

    // Handle update category form submit
    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable Long id,
                                 @ModelAttribute("category") CategoryDTO updatedCategory,
                                 RedirectAttributes redirectAttributes) {
        categoryService.updateCategory(id, updatedCategory);
        redirectAttributes.addFlashAttribute("success", "Category updated successfully!");
        return "redirect:/categories/view/view";
    }
}
