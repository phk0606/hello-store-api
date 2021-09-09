package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
//@Rollback(value = false)
@Slf4j
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void getOrder() {
        OrderDto order = orderRepository.getOrder(5l);

        log.debug("order: {}", order);
    }
}