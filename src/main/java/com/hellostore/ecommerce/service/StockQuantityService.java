package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ProductOptionDto;
import com.hellostore.ecommerce.dto.StockQuantityDto;
import com.hellostore.ecommerce.dto.StockQuantitySearchCondition;
import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.entity.ProductOption;
import com.hellostore.ecommerce.entity.StockQuantity;
import com.hellostore.ecommerce.repository.ProductOptionRepository;
import com.hellostore.ecommerce.repository.ProductRepository;
import com.hellostore.ecommerce.repository.StockQuantityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StockQuantityService {

    private final StockQuantityRepository stockQuantityRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    public Page<StockQuantityDto> getStockQuantities(
            StockQuantitySearchCondition stockQuantitySearchCondition, Pageable pageable) {

        return stockQuantityRepository.getStockQuantities(stockQuantitySearchCondition, pageable);
    }

    @Transactional
    public void modifyStockQuantity(StockQuantityDto stockQuantityDto) {
        stockQuantityRepository.modifyStockQuantity(stockQuantityDto);
    }

    @Transactional
    public void createStockQuantity(StockQuantityDto stockQuantityDto) {

        Product product = productRepository.getProduct(stockQuantityDto.getProductId());
        ProductOption firstOption = productOptionRepository.getProductOption(stockQuantityDto.getFirstOptionId());
        ProductOption secondOption = productOptionRepository.getProductOption(stockQuantityDto.getSecondOptionId());

        StockQuantity stockQuantity = StockQuantity.builder()
                .product(product)
                .firstOption(firstOption)
                .secondOption(secondOption)
                .stockQuantity(stockQuantityDto.getStockQuantity())
                .build();

        stockQuantityRepository.createStockQuantity(stockQuantity);
    }
}
