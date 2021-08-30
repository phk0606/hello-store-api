package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.CategoryDto;
import com.hellostore.ecommerce.dto.CategorySelectDto;
import com.hellostore.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/getCategories")
    public List<CategoryDto> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/getCategory")
    public List<CategorySelectDto> getCategory(@RequestParam(required = false) Long parentId) {
        return categoryService.getCategory(parentId);
    }

    @PostMapping("/createCategory")
    public void createCategory(@RequestBody CategoryDto productCategoryDto) {
        log.debug("productCategoryDto: {}", productCategoryDto);
        categoryService.createCategory(productCategoryDto);
    }

    @PostMapping("/modifyCategory")
    public void modifyCategory(@RequestBody CategoryDto productCategoryDto) {
        categoryService.modifyCategory(productCategoryDto);
    }

    @PostMapping("/deleteCategory")
    public void deleteCategory(@RequestBody CategoryDto productCategoryDto) {
        categoryService.deleteCategory(productCategoryDto);
    }
}