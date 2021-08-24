package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ProductCategoryDto;
import com.hellostore.ecommerce.entity.ProductCategory;
import com.hellostore.ecommerce.repository.ProductCategoryDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryDslRepository repository;

    public List<ProductCategoryDto> getProductCategories() {

        List<ProductCategory> allWithQuerydsl = repository.findAllWithQuerydsl();
        return allWithQuerydsl.stream().map(ProductCategoryDto::new).collect(Collectors.toList());
    }
}
