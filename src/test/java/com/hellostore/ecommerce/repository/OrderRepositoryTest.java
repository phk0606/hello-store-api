package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.OrderProductDto;
import com.hellostore.ecommerce.entity.Order;
import com.hellostore.ecommerce.entity.OrderProduct;
import com.hellostore.ecommerce.entity.ProductImage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Transactional
//@Rollback(value = false)
@Slf4j
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void getOrder() {
        Order order = orderRepository.findOne(5l);

        log.debug("order: {}", order);
    }

}