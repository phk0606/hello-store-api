package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.entity.ProductCategory;
import com.hellostore.ecommerce.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> getProductCategories() {

        return productCategoryRepository.findAll();
    }
}
