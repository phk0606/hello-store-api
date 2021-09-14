package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.OrderDto;
import com.hellostore.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/createOrder")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Long order(@RequestBody OrderDto orderDto) {
        log.debug("orderDto: {}", orderDto);

        return orderService.order(orderDto);
    }

    @GetMapping("/getOrder")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public OrderDto getOrder(@RequestParam Long orderId) throws IOException {

        return orderService.getOrder(orderId);
    }

    @GetMapping("/getOrdersByUsername")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<OrderDto> getOrdersByUsername(@RequestParam String username) throws IOException {
        return orderService.getOrdersByUsername(username);
    }
}
