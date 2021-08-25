package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.CategoryDto;
import com.hellostore.ecommerce.dto.CategorySelectDto;
import com.hellostore.ecommerce.entity.Category;
import com.hellostore.ecommerce.repository.CategoryDslRepository;
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
public class CategoryService {

    private final CategoryDslRepository repository;

    public List<CategoryDto> getCategories() {

        List<Category> categories = repository.getCategories();
        log.debug("!! categories: {}",categories);
        return categories.stream().map(CategoryDto::new).collect(Collectors.toList());
    }

    public List<CategorySelectDto> getProductCategory(Long parentId) {

        List<Category> productCategory = repository.getCategory(parentId);
        log.debug("productCategory: {}",productCategory);
        return productCategory.stream().map(CategorySelectDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void createProductCategory(final CategoryDto categoryDto) {

        Long id = categoryDto.getId();
        Long parentId = categoryDto.getParentId();

        Integer CategoryMaxSequence = repository.getCategoryMaxSequence(id, parentId);
        categoryDto.setSequence(CategoryMaxSequence + 1);
        log.debug("productCategoryDto: {}", categoryDto);
//        ProductCategory category = modelMapper.map(productCategoryDto, ProductCategory.class);
        Category productCategory = categoryDto.toEntity(categoryDto);
        log.debug("productCategory: {}", productCategory);
        repository.createProductCategory(productCategory);
    }

    @Transactional
    public void modifyProductCategory(final CategoryDto productCategoryDto) {
        Category productCategory = productCategoryDto.toEntity(productCategoryDto);
        repository.modifyProductCategory(productCategory);
    }

    @Transactional
    public void deleteProductCategory(final CategoryDto categoryDto) {
        Category category = categoryDto.toEntity(categoryDto);
        repository.deleteProductCategory(category);
    }
}
