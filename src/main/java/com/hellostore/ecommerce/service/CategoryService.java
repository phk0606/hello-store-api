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

    public List<CategorySelectDto> getCategory(Long parentId) {

        List<Category> category = repository.getCategory(parentId);
        log.debug("productCategory: {}",category);
        return category.stream().map(CategorySelectDto::new).collect(Collectors.toList());
    }

    public Category getCategoryOne(Long id) {

        return repository.getCategoryOne(id);
    }

    @Transactional
    public void createCategory(final CategoryDto categoryDto) {

        Long id = categoryDto.getId();
        Long parentId = categoryDto.getParentId();

        Integer CategoryMaxSequence = repository.getCategoryMaxSequence(id, parentId);
        categoryDto.setSequence(CategoryMaxSequence + 1);
        log.debug("categoryDto: {}", categoryDto);
//        ProductCategory category = modelMapper.map(productCategoryDto, ProductCategory.class);
        Category category = categoryDto.toEntity(categoryDto);
        log.debug("category: {}", category);
        repository.createCategory(category);
    }

    @Transactional
    public void modifyCategory(final CategoryDto categoryDto) {
        Category category = categoryDto.toEntity(categoryDto);
        repository.modifyCategory(category);
    }

    @Transactional
    public void deleteCategory(final CategoryDto categoryDto) {
        Category category = categoryDto.toEntity(categoryDto);
        repository.deleteCategory(category);
    }
}
