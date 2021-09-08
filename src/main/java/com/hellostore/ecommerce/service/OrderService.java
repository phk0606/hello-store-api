package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.OrderDto;
import com.hellostore.ecommerce.dto.OrderProductDto;
import com.hellostore.ecommerce.entity.*;
import com.hellostore.ecommerce.enumType.DeliveryStatus;
import com.hellostore.ecommerce.enumType.PaymentMethodType;
import com.hellostore.ecommerce.enumType.PaymentStatus;
import com.hellostore.ecommerce.repository.OrderRepository;
import com.hellostore.ecommerce.repository.ProductRepository;
import com.hellostore.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long order(OrderDto orderDto) {

        //엔티티 조회
        Optional<User> user = userRepository.findById(orderDto.getUserNo());
        Product product = productRepository.getProduct(orderDto.getProductId());

        //배송 정보 생성
        Address address1 = orderDto.getDelivery().getAddress();
        Address address = new Address(
                address1.getZoneCode(),
                address1.getAddress(),
                address1.getDetailAddress());
        Delivery delivery = Delivery.builder().address(address).status(DeliveryStatus.READY).build();

        List<OrderProductDto> orderProductDtos = orderDto.getOrderProducts();

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderProductDto orderProductDto : orderProductDtos) {

            //주문상품 생성
            orderProducts.add(OrderProduct.createOrderProduct(product, orderProductDto));
        }

        //주문 생성
        Order order
                = Order.createOrder(user, delivery, orderProducts,
                 orderDto.getPaymentMethodType(),
                 orderDto.getPaymentStatus());

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findOne(orderId);

        order.cancel();
    }

//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAll(orderSearch);
//    }
}
