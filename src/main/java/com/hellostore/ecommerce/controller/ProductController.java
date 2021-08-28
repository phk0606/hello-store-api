package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.ProductDto;
import com.hellostore.ecommerce.entity.Category;
import com.hellostore.ecommerce.service.CategoryService;
import com.hellostore.ecommerce.service.ProductImageService;
import com.hellostore.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final ProductImageService productImageService;

    @PostMapping("/createProduct")
    public void createProduct(@RequestPart ProductDto productDto, @RequestParam List<MultipartFile> productImages) {

        // 카테고리 조회
        Category category = categoryService.getCategoryOne(productDto.getCategoryId());

        // 상품 저장
        productService.createProduct(productDto, category);

        // 상품 옵션 저장

        // 상품 이미지 저장

        log.debug("productDto: {}", productDto);

        productImageService.uploadProductImage(productImages);

    }
}
