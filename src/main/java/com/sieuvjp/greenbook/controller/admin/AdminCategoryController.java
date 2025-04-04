package com.sieuvjp.greenbook.controller.admin;

import com.sieuvjp.greenbook.dto.CategoryDTO;
import com.sieuvjp.greenbook.entity.Category;
import com.sieuvjp.greenbook.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        List<CategoryDTO> categories = categoryService.findAll().stream()
                .map(CategoryDTO::fromEntity)
                .collect(Collectors.toList());

        model.addAttribute("categories", categories);
        return "admin/categories/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new CategoryDTO());
        return "admin/categories/form";
    }

    @PostMapping("/create")
    public String createCategory(@Valid @ModelAttribute("category") CategoryDTO categoryDTO,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {

        // Check for validation errors
        if (result.hasErrors()) {
            return "admin/categories/form";
        }

        // Check if category name already exists
        if (categoryService.existsByName(categoryDTO.getName())) {
            result.rejectValue("name", "error.category", "Category name already exists");
            return "admin/categories/form";
        }

        // Set as active by default
        categoryDTO.setActive(true);

        // Save category
        Category category = categoryDTO.toEntity();
        categoryService.save(category);

        redirectAttributes.addFlashAttribute("successMessage", "Category created successfully");
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));

        model.addAttribute("category", CategoryDTO.fromEntity(category));
        return "admin/categories/form";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Long id,
                                 @Valid @ModelAttribute("category") CategoryDTO categoryDTO,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {

        // Check for validation errors
        if (result.hasErrors()) {
            return "admin/categories/form";
        }

        // Get existing category
        Category existingCategory = categoryService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));

        // Check if category name already exists (except for this category)
        if (!existingCategory.getName().equals(categoryDTO.getName()) &&
                categoryService.existsByName(categoryDTO.getName())) {
            result.rejectValue("name", "error.category", "Category name already exists");
            return "admin/categories/form";
        }

        // Save category
        categoryDTO.setId(id);
        Category category = categoryDTO.toEntity();
        categoryService.save(category);

        redirectAttributes.addFlashAttribute("successMessage", "Category updated successfully");
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Category deleted successfully");
        return "redirect:/admin/categories";
    }

    @GetMapping("/toggle-status/{id}")
    public String toggleCategoryStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryService.toggleActiveStatus(id);
        redirectAttributes.addFlashAttribute("successMessage", "Category status updated successfully");
        return "redirect:/admin/categories";
    }
}