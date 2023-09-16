package com.nhom_4.backend.controllers;

import com.nhom_4.backend.entites.Category;
import com.nhom_4.backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Get all categories
    @GetMapping("/")
    public List<Category> all () { return categoryRepository.findAll();}

    @PostMapping("/")
    public Category add (@RequestBody Category category) {
        Category newCategory = categoryRepository.save(category);
        return newCategory;
    }
    @GetMapping("/{id}")
    public Optional<Category> get (@PathVariable Long id) {
        return categoryRepository.findById(id);
    }
    @PutMapping("/{id}")
    Category update(@RequestBody Category category, @PathVariable Long id) {

        return categoryRepository.findById(id)
                .map(categoryItem -> {
                    categoryItem.setName(category.getName());
                    categoryItem.setSummary(category.getIcon());
                    categoryItem.setIcon(category.getIcon());
                    return categoryRepository.save(categoryItem);
                })
                .orElseGet(() -> {
                    category.setId(id);
                    return categoryRepository.save(category);
                });
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        categoryRepository.deleteById(id);
    }
}
