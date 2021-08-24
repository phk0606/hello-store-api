package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.entity.ProductCategory;
import com.hellostore.ecommerce.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @GetMapping("/getProductCategories")
    public List<ProductCategory> getProductCategories() {
        return productCategoryService.getProductCategories();
    }
}
