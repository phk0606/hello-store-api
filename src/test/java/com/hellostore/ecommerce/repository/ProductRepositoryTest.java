package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.*;
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
    CategoryProductRepository categoryProductRepository;

    @Autowired
    CategoryDslRepository categoryDslRepository;

    @Test
    void createProduct() {

        ProductOption productOption1 = new ProductOption("color", "black", "Y");
        ProductOption productOption2 = new ProductOption("color", "white", "Y");
        ProductOption productOption3 = new ProductOption("color", "blue", "Y");
        ProductOption productOption4 = new ProductOption("size", "large", "Y");
        ProductOption productOption5 = new ProductOption("size", "medium", "Y");
        ProductOption productOption6 = new ProductOption("size", "small", "Y");
        List<ProductOption> productOptions = new ArrayList<>();
        productOptions.add(productOption1);
        productOptions.add(productOption2);
        productOptions.add(productOption3);
        productOptions.add(productOption4);
        productOptions.add(productOption5);
        productOptions.add(productOption6);

        Category category1 = categoryDslRepository.getCategoryOne(2l);

        Product product = new Product("백프린팅 반팔 티셔츠", 10000, 12000, 10, PointType.DEFAULT, null, ShippingFeeType.DEFAULT, null, true, false, true, "바디라인에 달라붙지 않아 시원하게 입기 좋은 데일리 반팔 티셔츠", productOptions, null, null, null, null, ProductShowType.SHOW);
        Long productId = productRepository.createProduct(product);
        Product product1 = productRepository.findProductById(productId);

        categoryProductRepository
                .createCategoryProduct(CategoryProduct.builder().category(category1).product(product1).build());

    }

    @Test
    public void 상품에_대한_카테고리_가져오기 () {
        QCategory category = QCategory.category;
        Tuple categoryForProduct = categoryDslRepository.getCategoryForProduct(65l);
        Object[] objects = categoryForProduct.toArray();
        for (Object object : objects) {

            log.debug("categoryForProduct: {}", object);
        }
    }
}