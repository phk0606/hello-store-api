package com.hellostore.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
@Slf4j
class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Test
    public void getProductCategories() {

        log.debug("productCategories: {}", categoryService.getCategories());
    }
}