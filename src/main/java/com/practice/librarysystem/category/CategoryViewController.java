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

    //  Handle GET /categories/view
    @GetMapping
    public String showCategoryForm(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("newCategory", new CategoryDTO()); // For the form
        return "categories";
    }

    // Handle POST /categories/view
    @PostMapping
    public String createCategory(@ModelAttribute("newCategory") CategoryDTO newCategory,
                                 RedirectAttributes redirectAttributes) {
        System.out.printf("Post /categories/view -createCategory() called with name: %s\n", newCategory.getName());
        categoryService.createCategory(newCategory);
        redirectAttributes.addFlashAttribute("success", "Category created successfully!");
        return "redirect:/categories/view";
    }

    // This seems unused, you might remove or fix the mapping if needed
    @GetMapping("/categories")
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("category", new CategoryDTO());
        return "categories";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        System.out.println("Post /categories/view -deleteCategory() called with id: " + id);
        categoryService.deleteCategory(id);
        redirectAttributes.addFlashAttribute("success", "Category deleted successfully!");
        return "redirect:/categories/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model) {
        System.out.println("Get /categories/view/edit/" + id + " called");
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);
        model.addAttribute("category", categoryDTO);
        return "edit-category";
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable Long id,
                                 @ModelAttribute("category") CategoryDTO updatedCategory,
                                 RedirectAttributes redirectAttributes) {
        System.out.println("Post /categories/view/update/" + id + " called");
        categoryService.updateCategory(id, updatedCategory);
        redirectAttributes.addFlashAttribute("success", "Category updated successfully!");
        return "redirect:/categories/view";
    }
}
