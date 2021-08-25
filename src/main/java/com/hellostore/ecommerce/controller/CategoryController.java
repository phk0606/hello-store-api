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

    private final CategoryService productCategoryService;

    @GetMapping("/getProductCategories")
    public List<CategoryDto> getProductCategories() {
        return productCategoryService.getCategories();
    }

    @GetMapping("/getProductCategory")
    public List<CategorySelectDto> getProductCategory(@RequestParam(required = false) Long parentId) {
        return productCategoryService.getProductCategory(parentId);
    }

    @PostMapping("/createProductCategory")
    public void createPrductCategory(@RequestBody CategoryDto productCategoryDto) {
        log.debug("productCategoryDto: {}", productCategoryDto);
        productCategoryService.createProductCategory(productCategoryDto);
    }

    @PostMapping("/modifyProductCategory")
    public void modifyProductCategory(@RequestBody CategoryDto productCategoryDto) {
        productCategoryService.modifyProductCategory(productCategoryDto);
    }

    @PostMapping("/deleteProductCategory")
    public void deleteProductCategory(@RequestBody CategoryDto productCategoryDto) {
        productCategoryService.deleteProductCategory(productCategoryDto);
    }
}
