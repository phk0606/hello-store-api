package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.ProductCategoryDto;
import com.hellostore.ecommerce.entity.ProductCategory;
import com.hellostore.ecommerce.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @GetMapping("/getProductCategories")
    public List<ProductCategoryDto> getProductCategories() {
        return productCategoryService.getProductCategories();
    }

    @PostMapping("/createProductCategory")
    public void createPrductCategory(@RequestBody ProductCategoryDto productCategoryDto) {
        productCategoryService.createProductCategory(productCategoryDto);
    }
}
