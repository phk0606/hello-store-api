package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ProductSearchCondition;
import com.hellostore.ecommerce.dto.ShopProductDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
@Slf4j
class ShopProductRepositoryTest {

    @Autowired
    ShopProductRepository shopProductRepository;

    @Test
    public void getProductsPage() {
        PageRequest pageRequest = PageRequest.of(0, 3);
        ProductSearchCondition productSearchCondition = new ProductSearchCondition();
//        productSearchCondition.setProductName("티셔츠");
        Page<ShopProductDto> productsPage = shopProductRepository.getProductsPage(productSearchCondition, pageRequest);

        List<ShopProductDto> content = productsPage.getContent();
        for (ShopProductDto shopProductDto : content) {
            log.debug("shopProductDto: {}", shopProductDto);
        }
    }
}