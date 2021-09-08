package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.OrderDto;
import com.hellostore.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/createOrder")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void order(@RequestBody OrderDto orderDto) {
        log.debug("orderDto: {}", orderDto);

//        orderService.order(orderDto);
    }
}
