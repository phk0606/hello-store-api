package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ProductOptionDto;
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
        List<ProductOptionDto> firstOptions
                = productOptionRepository.getProductOptions(1l, 1);
        List<ProductOptionDto> secondOptions
                = productOptionRepository.getProductOptions(1l, 2);

        for (ProductOptionDto secondOption : secondOptions) {
            for (ProductOptionDto firstOption : firstOptions) {

                StockQuantity stockQuantity = StockQuantity.builder()
                        .product(product)
                        .firstOption(ProductOptionDto.toEntity(firstOption))
                        .secondOption(ProductOptionDto.toEntity(secondOption))
                        .stockQuantity(120)
                        .build();
                stockQuantityRepository.createStockQuantity(stockQuantity);
            }
        }
    }

}