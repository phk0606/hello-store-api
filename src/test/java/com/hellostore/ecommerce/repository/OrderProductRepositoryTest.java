package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.OrderProductDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
class OrderProductRepositoryTest {

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Test
    public void getOrderProductsByUsername() {

        List<OrderProductDto> orderProductDtos
                = orderProductRepository.getOrderProductsByUsername("admin");

        log.debug("orderProductDtos: {}", orderProductDtos);
    }
}