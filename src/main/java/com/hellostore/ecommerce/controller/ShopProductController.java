package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.ProductDto;
import com.hellostore.ecommerce.dto.ProductImageDto;
import com.hellostore.ecommerce.dto.ProductSearchCondition;
import com.hellostore.ecommerce.dto.ShopProductDto;
import com.hellostore.ecommerce.service.ProductImageService;
import com.hellostore.ecommerce.service.ShopProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
@Slf4j
public class ShopProductController {

    private final ShopProductService shopProductService;
    private final ProductImageService productImageService;

    @PutMapping("/modifyClickCount")
    public void modifyClickCount(@RequestBody ProductDto productDto) {

        shopProductService.modifyClickCount(productDto.getProductId());
    }

    @GetMapping("/getProductsPageCondition")
    public Page<ShopProductDto> getProductsPageCondition(
            ProductSearchCondition productSearchCondition, Pageable pageable) throws IOException {

        log.debug("productSearchCondition: {}", productSearchCondition);
        return shopProductService.getProductsPageCondition(productSearchCondition, pageable);
    }

    @GetMapping("/getProductById")
    public ShopProductDto getProductById(Long productId) throws IOException {

        return shopProductService.getProductById(productId);
    }

    @GetMapping("/getListImage")
    public ProductImageDto getListImage(@RequestParam Long productId) throws IOException {

        return productImageService.getListImage(productId);
    }
}
