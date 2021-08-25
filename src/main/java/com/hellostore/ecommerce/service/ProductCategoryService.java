package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ProductCategoryDto;
import com.hellostore.ecommerce.dto.ProductCategorySelectDto;
import com.hellostore.ecommerce.entity.ProductCategory;
import com.hellostore.ecommerce.repository.ProductCategoryDslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public List<ProductCategoryDto> getProductCategories() {

        List<ProductCategory> productCategories = repository.getProductCategories();
        log.debug("!! productCategories: {}",productCategories);
        return productCategories.stream().map(ProductCategoryDto::new).collect(Collectors.toList());
    }

    public List<ProductCategorySelectDto> getProductCategory(Integer parentId) {

        List<ProductCategory> productCategory = repository.getProductCategory(parentId);
        log.debug("productCategory: {}",productCategory);
        return productCategory.stream().map(ProductCategorySelectDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void createProductCategory(final ProductCategoryDto productCategoryDto) {

        Integer id = productCategoryDto.getId();
        Integer parentId = productCategoryDto.getParentId();

        Integer CategoryMaxSequence = repository.getCategoryMaxSequence(id, parentId);
        productCategoryDto.setSequence(CategoryMaxSequence + 1);
        log.debug("productCategoryDto: {}", productCategoryDto);
//        ProductCategory category = modelMapper.map(productCategoryDto, ProductCategory.class);
        ProductCategory productCategory = productCategoryDto.toEntity(productCategoryDto);
        log.debug("productCategory: {}", productCategory);
        repository.createProductCategory(productCategory);
    }

    @Transactional
    public void modifyProductCategory(final ProductCategoryDto productCategoryDto) {
        ProductCategory productCategory = productCategoryDto.toEntity(productCategoryDto);
        repository.modifyProductCategory(productCategory);
    }

    @Transactional
    public void deleteProductCategory(final ProductCategoryDto productCategoryDto) {
        ProductCategory productCategory = productCategoryDto.toEntity(productCategoryDto);
        repository.deleteProductCategory(productCategory);
    }
}
