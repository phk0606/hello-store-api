package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.ProductOptionDto;
import com.hellostore.ecommerce.dto.StockQuantityDto;
import com.hellostore.ecommerce.dto.StockQuantitySearchCondition;
import com.hellostore.ecommerce.service.StockQuantityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stockQuantity")
@RequiredArgsConstructor
@Slf4j
public class StockQuantityController {

    private final StockQuantityService stockQuantityService;

    @PostMapping("/createStockQuantity")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void createStockQuantity(@RequestBody StockQuantityDto stockQuantityDto) {
        log.debug("stockQuantityDto: {}", stockQuantityDto);
        stockQuantityService.createStockQuantity(stockQuantityDto);
    }

    @GetMapping("/getStockQuantities")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Page<StockQuantityDto> getStockQuantities(
            StockQuantitySearchCondition stockQuantitySearchCondition, Pageable pageable) {

        return stockQuantityService.getStockQuantities(stockQuantitySearchCondition, pageable);
    }

    @GetMapping("/getFirstOptionsInStockQuantity")
    public List<ProductOptionDto> getFirstOptionsInStockQuantity(@RequestParam Long productId) {
        return stockQuantityService.getFirstOptionsInStockQuantity(productId);
    }

    @GetMapping("/getSecondOptionsInStockQuantity")
    public List<ProductOptionDto> getSecondOptionsInStockQuantity(
            @RequestParam Long productId, @RequestParam Long firstOptionId) {
        return stockQuantityService.getSecondOptionsInStockQuantity(productId, firstOptionId);
    }

    @PutMapping("/modifyStockQuantity")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void modifyStockQuantity(@RequestBody StockQuantityDto stockQuantityDto) {

        stockQuantityService.modifyStockQuantity(stockQuantityDto);
    }
}
