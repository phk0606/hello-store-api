package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.ProductCategoryImageDto;
import com.hellostore.ecommerce.dto.ProductDto;
import com.hellostore.ecommerce.entity.Category;
import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.service.CategoryService;
import com.hellostore.ecommerce.service.ProductImageService;
import com.hellostore.ecommerce.service.ProductOptionService;
import com.hellostore.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductOptionService productOptionService;
    private final ProductImageService productImageService;

    @PostMapping("/createProduct")
    public void createProduct(@RequestPart ProductDto productDto, @RequestParam(required = false) List<MultipartFile> productImages) {

        log.debug("productDto: {}", productDto);

        // 상품 저장
        Product product = productService.createProduct(productDto);

        // 상품 옵션 저장
        productOptionService.createProductOption(productDto.getFirstOptions(), productDto.getSecondOptions(), product);

        // 상품 이미지 저장
        productImageService.uploadProductImage(productImages, product);

    }

    @GetMapping("/searchProducts")
    public List<ProductCategoryImageDto> searchProducts() throws IOException {
        return productService.searchProducts();
    }


}