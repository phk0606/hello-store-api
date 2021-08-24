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
class ProductCategoryRepositoryTest {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Test
    public void testProductCategory() {

        ProductCategory productCategory1 = new ProductCategory(null, "OUTER", 1, "y");
        ProductCategory productCategory2 = new ProductCategory(null, "TOP", 2, "y");
        ProductCategory productCategory3 = new ProductCategory(null, "BOTTOM", 3, "y");
        ProductCategory productCategory4 = new ProductCategory(null, "DRESS", 4, "y");

        ProductCategory productCategory11 = new ProductCategory(1, "CARDIGAN", 1, "y");
        ProductCategory productCategory12 = new ProductCategory(1, "JAKET/JUMPER", 2, "y");
        ProductCategory productCategory13 = new ProductCategory(1, "COAT", 3, "y");
        ProductCategory productCategory14 = new ProductCategory(1, "VEST", 4, "y");
        ProductCategory productCategory15 = new ProductCategory(1, "PADDING", 5, "y");

        ProductCategory productCategory31 = new ProductCategory(3, "PANTS", 1, "y");
        ProductCategory productCategory32 = new ProductCategory(3, "SKIRT", 2, "y");

        productCategoryRepository.save(productCategory1);
        productCategoryRepository.save(productCategory2);
        productCategoryRepository.save(productCategory3);
        productCategoryRepository.save(productCategory4);

        productCategoryRepository.save(productCategory11);
        productCategoryRepository.save(productCategory12);
        productCategoryRepository.save(productCategory13);
        productCategoryRepository.save(productCategory14);
        productCategoryRepository.save(productCategory15);

        productCategoryRepository.save(productCategory31);
        productCategoryRepository.save(productCategory32);

        List<ProductCategory> all = productCategoryRepository.findAll();
        log.debug("productCategoryList: {}", all);

    }
}