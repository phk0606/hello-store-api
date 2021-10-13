package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.*;
import com.hellostore.ecommerce.entity.*;
import com.hellostore.ecommerce.enumType.OrderDeliveryStatus;
import com.hellostore.ecommerce.enumType.PaymentStatus;
import com.hellostore.ecommerce.enumType.PointUseDetailType;
import com.hellostore.ecommerce.enumType.PointUseType;
import com.hellostore.ecommerce.exception.NotEnoughStockException;
import com.hellostore.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final OderProductOptionRepository oderProductOptionRepository;
    private final ProductOptionRepository productOptionRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final BankAccountRepository bankAccountRepository;
    private final OrderPointRepository orderPointRepository;
    private final DeliveryRepository deliveryRepository;
    private final StockQuantityRepository stockQuantityRepository;

    @Transactional
    public Long order(OrderDto orderDto) {

        //엔티티 조회
        Optional<User> user = userRepository.findById(orderDto.getUserNo());

        List<OrderProductDto> orderProductDtos = orderDto.getOrderProducts();

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderProductDto orderProductDto : orderProductDtos) {
            Product product = productRepository.getProduct(orderProductDto.getProductId());
            //주문상품 생성
            orderProducts.add(OrderProduct.createOrderProduct(product, orderProductDto));

            // 상품 재고 빼기
            List<OrderProductOptionDto> productOptions = orderProductDto.getProductOptions();

            Long firstOptionId = 0l;
            Long secondOptionId = 0l;
            for (OrderProductOptionDto productOption : productOptions) {
                if(productOption.getOptionGroupNumber() == 1) {
                    firstOptionId = productOption.getOptionId();
                } else {
                    secondOptionId = productOption.getOptionId();
                }
            }
            StockQuantityDto stockQuantityDto = StockQuantityDto.builder()
                    .productId(orderProductDto.getProductId())
                    .firstOptionId(firstOptionId)
                    .secondOptionId(secondOptionId)
                    .stockQuantity(orderProductDto.getQuantity())
                    .build();

            boolean stockQuantityCheck = stockQuantityRepository.stockQuantityCheck(stockQuantityDto);
            if (stockQuantityCheck) {
                throw new NotEnoughStockException("재고 부족");
            }
            stockQuantityRepository.subtractStockQuantity(stockQuantityDto);
        }

        //배송 정보 생성
        Address address1 = orderDto.getDelivery().getAddress();
        Address address = new Address(
                address1.getZoneCode(),
                address1.getRoadAddress(),
                address1.getAddress(),
                address1.getDetailAddress());
        Delivery delivery = Delivery.builder()
                .address(address)
//                .status(DeliveryStatus.READY_SHIP)
                .phoneNumber(orderDto.getDelivery().getPhoneNumber())
                .recipientName(orderDto.getDelivery().getRecipientName())
                .requirement(orderDto.getDelivery().getRequirement())
                .build();

        if (!ObjectUtils.isEmpty(orderDto.getDepositAccountId())) {
            BankAccount bankAccount
                    = bankAccountRepository.getBankAccountById(orderDto.getDepositAccountId());
            orderDto.setBankAccount(bankAccount);
        }

        //주문 생성
        Order order
                = Order.createOrder(user, delivery, orderProducts, orderDto);

        //주문 저장
        Order order1 = orderRepository.save(order);

        // 포인트 추가
        if (orderDto.getAddPoint() > 0) {
            PointHistory pointHistory1 = PointHistory.builder()
                    .point(orderDto.getAddPoint())
                    .pointUseType(PointUseType.SAVE)
                    .pointUseDetailType(PointUseDetailType.PURCHASE)
                    .user(user.get()).build();
            pointHistoryRepository.createPointHistory(pointHistory1);
        }

        // 포인트 사용 저장
        if (orderDto.getUsedPoint() > 0) {
            PointHistory pointHistory = PointHistory.builder()
                    .point(orderDto.getUsedPoint() * -1)
                    .pointUseType(PointUseType.USE)
                    .pointUseDetailType(PointUseDetailType.PURCHASE)
                    .user(user.get()).build();
            pointHistoryRepository.createPointHistory(pointHistory);

            // 주문 완료 시 사용 포인트 표출
            orderPointRepository
                    .createOrderPoint(
                            OrderUsePoint.builder().order(order1).pointHistory(pointHistory).build());
        }

        return order.getId();
    }

    public List<OrderProductDto> getOrderProductsByUsername(String username) {
        return orderProductRepository.getOrderProductsByUsername(username);
    }

    public OrderDto getOrder(Long orderId) throws IOException {
        // order 가져오기
        OrderDto orderDto = orderRepository.getOrder(orderId);
//        log.debug("orderDto: {}", orderDto);
        // orderProducts 가져오기
        List<OrderProductDto> orderProductDtos = orderProductRepository.getOrderProducts(orderId);

        log.debug("orderProductDtos: {}", orderProductDtos);

        for (OrderProductDto orderProductDto : orderProductDtos) {
            // product image 가져오기
            orderProductDto.setImage(Files.readAllBytes(
                    Paths.get(orderProductDto.getFilePath(), orderProductDto.getFileName())));

            List<ProductOptionDto> productOptions1 =
                    productOptionRepository.getProductOptions(orderProductDto.getProductId(), 1);

            // product option 목록
            List<ProductOptionDto> productOptionDtos1 = new ArrayList<>();
            for (ProductOptionDto productOption : productOptions1) {
                productOptionDtos1.add(productOption);
            }

            orderProductDto.setFirstOptions(productOptionDtos1);

            List<ProductOptionDto> productOptions2 =
                    productOptionRepository.getProductOptions(orderProductDto.getProductId(), 2);

            List<ProductOptionDto> productOptionDtos2 = new ArrayList<>();
            for (ProductOptionDto productOption : productOptions2) {
                productOptionDtos2.add(productOption);
            }

            orderProductDto.setSecondOptions(productOptionDtos2);
        }

        // orderProductOptions 조회
        Map<Long, List<OrderProductOptionDto>> orderProductOptionMap
                = oderProductOptionRepository.getOrderProductOption(toOrderProductIds(orderProductDtos));

        log.debug("orderProductOptionMap: {}", orderProductOptionMap);
        // orderProduct 루프 돌면서 orderProductOption 추가
        orderProductDtos.forEach(o -> o.setProductOptions(orderProductOptionMap.get(o.getOrderProductId())));

        orderDto.setOrderProducts(orderProductDtos);

        // delivery 가져오기

        return orderDto;
    }

    private List<Long> toOrderProductIds(List<OrderProductDto> result) {
        return result.stream()
                .map(o -> o.getOrderProductId())
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findOne(orderId);
        order.cancel();

        List<OrderProductDto> orderProducts = orderProductRepository.getOrderProducts(orderId);

        for (OrderProductDto orderProduct : orderProducts) {

            // 주문 수량 복구
            List<OrderProductOptionDto> orderProductOptions
                    = oderProductOptionRepository.getOrderProductOptions(orderProduct.getOrderProductId());

            Long firstOptionId = 0l;
            Long secondOptionId = 0l;
            for (OrderProductOptionDto productOption : orderProductOptions) {
                if(productOption.getOptionGroupNumber() == 1) {
                    firstOptionId = productOption.getOptionId();
                } else {
                    secondOptionId = productOption.getOptionId();
                }
            }
            StockQuantityDto stockQuantityDto = StockQuantityDto.builder()
                    .productId(orderProduct.getProductId())
                    .firstOptionId(firstOptionId)
                    .secondOptionId(secondOptionId)
                    .stockQuantity(orderProduct.getQuantity())
                    .build();
            stockQuantityRepository.addStockQuantity(stockQuantityDto);
        }
    }

    @Transactional
    public void modifyOrderDeliveryStatus(List<Long> orderIds, OrderDeliveryStatus orderDeliveryStatus) {
        orderRepository.modifyOrderDeliveryStatus(orderIds, orderDeliveryStatus);
    }

    @Transactional
    public void modifyPaymentStatus(List<Long> orderIds, PaymentStatus paymentStatus) {
        orderRepository.modifyPaymentStatus(orderIds, paymentStatus);
    }

    public Page<OrderDto> getOrdersByUsername(Pageable pageable, OrderSearchCondition orderSearchCondition) throws IOException {
        // orders 가져오기
        Page<OrderDto> orders = orderRepository.getOrdersByUsername(pageable, orderSearchCondition);

        log.debug("orders: {}", orders);
        // orderProduct, product image 가져오기
        List<OrderProductDto> orderProduct = orderRepository.getOrderProduct(toOrderIds(orders.getContent()));

        for (OrderProductDto orderProductDto : orderProduct) {
            orderProductDto.setImage(
                    Files.readAllBytes(
                            Paths.get(orderProductDto.getFilePath(), orderProductDto.getFileName())));
        }

        // orderProductOptions 조회
        Map<Long, List<OrderProductOptionDto>> orderProductOptionMap
                = oderProductOptionRepository.getOrderProductOption(toOrderProductIds(orderProduct));

        orderProduct.forEach(o -> o.setProductOptions(orderProductOptionMap.get(o.getOrderProductId())));

        Map<Long, List<OrderProductDto>> collect = orderProduct.stream()
                .collect(Collectors.groupingBy(OrderProductDto::getOrderId));

        orders.forEach(o -> o.setOrderProducts(collect.get(o.getOrderId())));

        return orders;
    }

    private List<Long> toOrderIds(List<OrderDto> result) {
        return result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());
    }

    public Page<OrderDto> getOrders(Pageable pageable, OrderSearchCondition orderSearchCondition) throws IOException {
        // orders 가져오기
        Page<OrderDto> orders = orderRepository.getOrders(pageable, orderSearchCondition);

        log.debug("orders: {}", orders);
        // orderProduct, product image 가져오기
        List<OrderProductDto> orderProduct = orderRepository.getOrderProduct(toOrderIds(orders.getContent()));

        Map<Long, List<OrderProductDto>> collect = orderProduct.stream()
                .collect(Collectors.groupingBy(OrderProductDto::getOrderId));

        orders.forEach(o -> o.setOrderProducts(collect.get(o.getOrderId())));

        return orders;
    }

    @Transactional
    public void modifyOrdererPhoneNumber(OrderDto orderDto) {
        orderRepository.modifyOrdererPhoneNumber(orderDto);
    }

    @Transactional
    public void modifyDeliveryInfo(OrderDto orderDto) {
        orderRepository.modifyDeliveryInfo(orderDto);
    }

    @Transactional
    public void modifyOrder(OrderDto orderDto) {

        deliveryRepository.modifyDelivery(orderDto);

        List<OrderProductDto> orderProductDtos = orderDto.getOrderProducts();
        for (OrderProductDto orderProductDto : orderProductDtos) {

            OrderProduct orderProduct
                    = orderProductRepository.getOrderProductById(orderProductDto.getOrderProductId());

            oderProductOptionRepository.removeProductOption(orderProductDto.getOrderProductId());

            List<OrderProductOptionDto> orderProductOptionDtos = orderProductDto.getProductOptions();

            for (OrderProductOptionDto orderProductOptionDto : orderProductOptionDtos) {

                OrderProductOption orderProductOption = OrderProductOption.builder()
                        .orderProduct(orderProduct)
                        .optionId(orderProductOptionDto.getOptionId())
                        .optionGroupNumber(orderProductOptionDto.getOptionGroupNumber())
                        .optionName(orderProductOptionDto.getOptionName())
                        .optionValue(orderProductOptionDto.getOptionValue())
                        .build();

                oderProductOptionRepository.createOrderProductOption(orderProductOption);
            }
        }

        orderRepository.modifyOrder(orderDto);
    }

//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAll(orderSearch);
//    }
}
