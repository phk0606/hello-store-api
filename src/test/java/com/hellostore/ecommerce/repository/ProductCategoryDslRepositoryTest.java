package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
@Slf4j
class ProductCategoryDslRepositoryTest {

    @Autowired
    CategoryDslRepository repository;

    @Test
    public void testGetProductCategory() {

        List<Category> allWithQuerydsl = repository.getCategories();
        log.debug("getProductCategories: {}", allWithQuerydsl);
    }

    @Test
    public void testInsertProductCategory() {
        Category productCategory = new Category(null,null, "테스트 카테고리", 9999, "Y");
        repository.createProductCategory(productCategory);
    }

    @Test
    public void testGetFirstCategoryMaxSequence() {
        Long categoryId = 1l;
        Integer categoryMaxSequence = repository.getCategoryMaxSequence(categoryId, 11l);
        log.debug("categoryMaxSequence: {}", categoryMaxSequence);

        Long categoryId1 = 10l;
        Integer categoryMaxSequence1 = repository.getCategoryMaxSequence(categoryId1, 11l);
        log.debug("categoryMaxSequence1: {}", categoryMaxSequence1);
    }

    @Test
    public void testModifyFirstCategory() {
        Category productCategory = new Category(20l,null, "테스트 카테고리", 9999, "N");
        repository.modifyProductCategory(productCategory);
    }

    @Test
    public void testDeleteFirstCategory() {
        Category productCategory = new Category(13l,null, "테스트 카테고리", 9999, "N");
        repository.deleteProductCategory(productCategory);
    }

    @Test
    public void testGetCategory() {
        List<Category> productCategory = repository.getCategory(1l);
        log.debug("productCategory: {}", productCategory);
    }
}