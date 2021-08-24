package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ProductCategoryDto;
import com.hellostore.ecommerce.entity.ProductCategory;
import com.hellostore.ecommerce.repository.ProductCategoryDslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductCategoryService {

    private final ProductCategoryDslRepository repository;

    public List<ProductCategoryDto> getProductCategories() {

        List<ProductCategory> productCategories = repository.getProductCategories();
        return productCategories.stream().map(ProductCategoryDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void createProductCategory(final ProductCategoryDto productCategoryDto) {

        Integer CategoryMaxSequence = repository.getCategoryMaxSequence(productCategoryDto.getId());
        productCategoryDto.setSequence(CategoryMaxSequence + 1);
        ProductCategory productCategory = productCategoryDto.toEntity(productCategoryDto);
        log.debug("productCategory: {}", productCategory);
        repository.createProductCategory(productCategory);
    }

}
