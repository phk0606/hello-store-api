package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ProductCategoryDto;
import com.hellostore.ecommerce.entity.ProductCategory;
import com.hellostore.ecommerce.repository.ProductCategoryDslRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryDslRepository repository;
    private final ModelMapper modelMapper;

    public List<ProductCategoryDto> getProductCategories() {

        List<ProductCategory> allWithQuerydsl = repository.getProductCategories();
        return allWithQuerydsl.stream().map(ProductCategoryDto::new).collect(Collectors.toList());
    }

    public void createProductCategory(ProductCategoryDto productCategoryDto) {

        ProductCategory productCategory = modelMapper.map(productCategoryDto, ProductCategory.class);
        repository.createProductCategory(productCategory);
    }
}
