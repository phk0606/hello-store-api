package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
@Slf4j
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    public void removeProducts() {

        Product product = productService.getProductById(199l);
        List<Product> products = new ArrayList<>();
        products.add(product);
        for (Product product1 : products) {

            productService.removeProduct(product1.getId());
        }
    }
}