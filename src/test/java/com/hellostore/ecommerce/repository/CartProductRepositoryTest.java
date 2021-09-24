package com.hellostore.ecommerce.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
@Slf4j
class CartProductRepositoryTest {

    @Autowired
    CartProductRepository cartProductRepository;

    @Test
    public void getCartProductCount() {
        Long count = cartProductRepository.getCartProductCount("hkpark");
        log.debug("count: {}",count);
    }
}