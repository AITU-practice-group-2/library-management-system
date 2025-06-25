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



    @PostMapping
    public String createCategory(@ModelAttribute("newCategory") CategoryDTO newCategory,
                                 RedirectAttributes redirectAttributes) {
        System.out.printf("Post /categories/view -createCategory() called with name: %s\n", newCategory.getName());
        categoryService.createCategory(newCategory);
        redirectAttributes.addFlashAttribute("success", "Category created successfully!");
        return "redirect:/categories/view";
    }


    @GetMapping
    public String showCategoryList(Model model) {
        System.out.println("Get /categories/view -show category list");
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("newCategory", new CategoryDTO());
        return "categories/view";
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
        System.out.println("Get//categories/view -showEditCategoryForm() called with id: " + id);
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);
        model.addAttribute("category", categoryDTO);
        return "edit-category";
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable Long id,
                                 @ModelAttribute("category") CategoryDTO updatedCategory,
                                 RedirectAttributes redirectAttributes) {
        System.out.println("Post /categories/view -updateCategory() called with id: " + id);
        categoryService.updateCategory(id, updatedCategory);
        redirectAttributes.addFlashAttribute("success", "Category updated successfully!");
        return "redirect:/categories/view";
    }
}
