package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.entity.ProductOption;
import com.hellostore.ecommerce.entity.StockQuantity;
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
class StockQuantityRepositoryTest {

    @Autowired
    StockQuantityRepository stockQuantityRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductOptionRepository productOptionRepository;

    @Test
    public void createStockQuantityTest() {

        Product product = productRepository.getProduct(1l);
        List<ProductOption> firstOptions
                = productOptionRepository.getProductOptions(1l, 1);
        List<ProductOption> secondOptions
                = productOptionRepository.getProductOptions(1l, 2);

        for (ProductOption secondOption : secondOptions) {
            for (ProductOption firstOption : firstOptions) {

                StockQuantity stockQuantity = StockQuantity.builder()
                        .product(product)
                        .firstOption(firstOption)
                        .secondOption(secondOption)
                        .stockQuantity(120)
                        .build();
                stockQuantityRepository.createStockQuantity(stockQuantity);
            }
        }
    }

}