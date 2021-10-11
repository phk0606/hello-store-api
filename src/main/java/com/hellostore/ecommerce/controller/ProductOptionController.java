package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.ProductOptionDto;
import com.hellostore.ecommerce.service.ProductOptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/productOption")
@RequiredArgsConstructor
@Slf4j
public class ProductOptionController {

    private final ProductOptionService productOptionService;

    @GetMapping("/getFirstOptions")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<ProductOptionDto> getFirstOptions(Long productId) {
        return productOptionService.getFirstOptions(productId);
    }

    @GetMapping("/getSecondOptions")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<ProductOptionDto> getSecondOptions(Long productId) {
        return productOptionService.getSecondOptions(productId);
    }
}
