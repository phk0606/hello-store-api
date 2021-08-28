package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ProductDto;
import com.hellostore.ecommerce.entity.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductService {

    @Transactional
    public void createProduct(ProductDto productDto, Category category) {

        productDto.toEntity(productDto, category);
    }
}
