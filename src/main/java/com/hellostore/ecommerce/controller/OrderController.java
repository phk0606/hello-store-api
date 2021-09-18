package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.OrderDto;
import com.hellostore.ecommerce.dto.OrderSearchCondition;
import com.hellostore.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<OrderDto> getOrdersByUsername(Pageable pageable, OrderSearchCondition orderSearchCondition) throws IOException {
        return orderService.getOrdersByUsername(pageable, orderSearchCondition);
    }

    @GetMapping("/getOrders")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Page<OrderDto> getOrders(Pageable pageable, OrderSearchCondition orderSearchCondition) throws IOException {
        log.debug("orderSearchCondition: {}", orderSearchCondition);
        return orderService.getOrders(pageable, orderSearchCondition);
    }

    @PutMapping("/modifyOrdererPhoneNumber")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void modifyOrdererPhoneNumber(@RequestBody OrderDto orderDto) {
        log.debug("orderDto: {}", orderDto);
        orderService.modifyOrdererPhoneNumber(orderDto);
    }

    @PutMapping("/modifyDeliveryInfo")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void modifyDeliveryInfo(@RequestBody OrderDto orderDto) {
        log.debug("orderDto: {}", orderDto);
        orderService.modifyDeliveryInfo(orderDto);
    }

    @PutMapping("/orderCancel")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void orderCancel(@RequestBody OrderDto orderDto) {
        log.debug("orderDto: {}", orderDto);
        orderService.cancelOrder(orderDto.getOrderId());
    }

    @PutMapping("/modifyOrderDeliveryStatus")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void modifyOrderDeliveryStatus(@RequestBody OrderDto orderDto) {

        orderService.modifyOrderDeliveryStatus(
                orderDto.getOrderIds(), orderDto.getOrderDeliveryStatus());
    }

    @PutMapping("/modifyPaymentStatus")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void modifyPaymentStatus(@RequestBody OrderDto orderDto) {

        orderService.modifyPaymentStatus(orderDto.getOrderIds(), orderDto.getPaymentStatus());
    }
}
