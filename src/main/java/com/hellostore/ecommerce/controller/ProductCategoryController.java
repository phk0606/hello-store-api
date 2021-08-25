package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.ProductCategoryDto;
import com.hellostore.ecommerce.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @GetMapping("/getProductCategories")
    public List<ProductCategoryDto> getProductCategories() {
        return productCategoryService.getProductCategories();
    }

    @PostMapping("/createProductCategory")
    public void createPrductCategory(@RequestBody ProductCategoryDto productCategoryDto) {
        log.debug("productCategoryDto: {}", productCategoryDto);
        productCategoryService.createProductCategory(productCategoryDto);
    }

    @PostMapping("/modifyProductCategory")
    public void modifyProductCategory(@RequestBody ProductCategoryDto productCategoryDto) {
        productCategoryService.modifyProductCategory(productCategoryDto);
    }

    @PostMapping("/deleteProductCategory")
    public void deleteProductCategory(@RequestBody ProductCategoryDto productCategoryDto) {
        productCategoryService.deleteProductCategory(productCategoryDto);
    }
}
