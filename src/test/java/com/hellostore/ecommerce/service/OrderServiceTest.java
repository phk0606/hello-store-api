package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.io.IOException;

@SpringBootTest
@Transactional
@Slf4j
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Test
    public void getOrder() throws IOException {
        OrderDto orderDto = orderService.getOrder(6l);

        log.debug("orderDto: {}", orderDto);
    }
}