package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.ProductCategory;
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
    ProductCategoryDslRepository repository;

    @Test
    public void testGetProductCategory() {

        List<ProductCategory> allWithQuerydsl = repository.findAllWithQuerydsl();
        log.debug("getProductCategories: {}", allWithQuerydsl);
    }

    @Test
    public void testInsertProductCategory() {
        ProductCategory productCategory = new ProductCategory(null, "테스트 카테고리", 9999, "Y");
        repository.create(productCategory);

    }
}