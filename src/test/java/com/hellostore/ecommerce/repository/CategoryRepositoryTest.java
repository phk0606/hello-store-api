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
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository productCategoryRepository;

    @Test
    public void testProductCategory() {

        Category productCategory1 = new Category(null,null, "OUTER", 1, "Y");
        Category productCategory2 = new Category(null,null, "TOP", 2, "Y");
        Category productCategory3 = new Category(null,null, "BOTTOM", 3, "Y");
        Category productCategory4 = new Category(null,null, "DRESS", 4, "Y");

        Category productCategory11 = new Category(null,productCategory1, "CARDIGAN", 1, "Y");
        Category productCategory12 = new Category(null,productCategory1, "JAKET/JUMPER", 2, "Y");
        Category productCategory13 = new Category(null,productCategory1, "COAT", 3, "Y");
        Category productCategory14 = new Category(null,productCategory1, "VEST", 4, "Y");
        Category productCategory15 = new Category(null,productCategory1, "PADDING", 5, "y");

        Category productCategory31 = new Category(null,productCategory3, "PANTS", 1, "Y");
        Category productCategory32 = new Category(null,productCategory3, "SKIRT", 2, "Y");

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

        List<Category> all = productCategoryRepository.findAll();
        log.debug("productCategoryList: {}", all);

    }
}