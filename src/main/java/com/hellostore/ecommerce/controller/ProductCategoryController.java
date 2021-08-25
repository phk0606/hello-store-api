package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.ProductCategoryDto;
import com.hellostore.ecommerce.dto.ProductCategorySelectDto;
import com.hellostore.ecommerce.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getProductCategory")
    public List<ProductCategorySelectDto> getProductCategory(@RequestParam(required = false) Integer parentId) {
        return productCategoryService.getProductCategory(parentId);
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
