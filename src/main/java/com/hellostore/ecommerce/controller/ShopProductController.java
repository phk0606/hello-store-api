package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.ProductSearchCondition;
import com.hellostore.ecommerce.dto.ShopProductDto;
import com.hellostore.ecommerce.service.ShopProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
@Slf4j
public class ShopProductController {

    private final ShopProductService shopProductService;

    @GetMapping("/getProductsPageCondition")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public Page<ShopProductDto> getProductsPageCondition(
            ProductSearchCondition productSearchCondition, Pageable pageable) throws IOException {

        return shopProductService.getProductsPageCondition(productSearchCondition, pageable);
    }

    @GetMapping("/getProductDetail")
    public ShopProductDto getProductDetail(Long productId) throws IOException {

        return shopProductService.getProductById(productId);
    }
}
