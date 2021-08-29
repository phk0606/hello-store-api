package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.*;
import com.hellostore.ecommerce.enumType.ImageType;
import com.hellostore.ecommerce.enumType.PointType;
import com.hellostore.ecommerce.enumType.ProductShowType;
import com.hellostore.ecommerce.enumType.ShippingFeeType;
import com.querydsl.core.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
@Slf4j
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryDslRepository categoryDslRepository;

    @Autowired
    ProductOptionRepository productOptionRepository;

    @Autowired CategoryProductRepository categoryProductRepository;


    @Test
    void createProduct() {

        Category category1 = categoryDslRepository.getCategoryOne(7l);

        Product product = new Product("긴팔 티셔츠", 10000, 12000, 10, PointType.DEFAULT, null, ShippingFeeType.DEFAULT, null, true, false, true, "바디라인에 달라붙지 않아 시원하게 입기 좋은 데일리 반팔 티셔츠",  null, null, null, ProductShowType.SHOW);
        Product product1 = productRepository.createProduct(product);

        categoryProductRepository.createCategoryProduct(CategoryProduct.builder()
                .product(product)
                .category(category1).build());

        ProductOption productOption1 = new ProductOption(product1, "color", "black");
        ProductOption productOption2 = new ProductOption(product1, "color", "white");
        ProductOption productOption3 = new ProductOption(product1, "color", "blue");
        ProductOption productOption4 = new ProductOption(product1, "size", "large");
        ProductOption productOption5 = new ProductOption(product1, "size", "medium");
        ProductOption productOption6 = new ProductOption(product1, "size", "small");

        productOptionRepository.createProductOption(productOption1);
        productOptionRepository.createProductOption(productOption2);
        productOptionRepository.createProductOption(productOption3);
        productOptionRepository.createProductOption(productOption4);
        productOptionRepository.createProductOption(productOption5);
        productOptionRepository.createProductOption(productOption6);

    }

    @Test
    public void getProductWithCategory() {
        Product productById = productRepository.findProductById(65l);
        log.debug("productById: {}", productById);
    }

    @Test
    public void test() {
        log.debug("ImageType: {}", ImageType.LIST);
    }
}