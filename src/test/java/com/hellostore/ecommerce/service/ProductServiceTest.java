package com.hellostore.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
@Slf4j
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    public void removeProducts() {

        List<Long> productIds = new ArrayList<>();
        productIds.add(10l);
        productIds.add(11l);
        productIds.add(12l);

        productService.removeProducts(productIds);

    }
}