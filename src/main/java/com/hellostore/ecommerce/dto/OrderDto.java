package com.hellostore.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hellostore.ecommerce.entity.Address;
import com.hellostore.ecommerce.entity.BankAccount;
import com.hellostore.ecommerce.entity.Order;
import com.hellostore.ecommerce.enumType.OrderDeliveryStatus;
import com.hellostore.ecommerce.enumType.OrderType;
import com.hellostore.ecommerce.enumType.PaymentMethodType;
import com.hellostore.ecommerce.enumType.PaymentStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class OrderDto {

    private Long orderId;
    private List<Long> orderIds;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderCancelDate;
    private Long userNo;
    private String username;
    private String name;
    private String phoneNumber;

    @Setter
    private OrderType orderType;
    private String orderTypeValue;
    private PaymentMethodType paymentMethodType;
    private String paymentMethodTypeValue;
    private PaymentStatus paymentStatus;
    private String paymentStatusValue;
    private Integer paymentPrice;
    private Integer usedPoint;
    private Integer addPoint;

    private String depositAccount;
    private Long depositAccountId;
    @Setter
    private BankAccount bankAccount;

    private String depositorName;
    private LocalDate depositDueDate;
    private OrderDeliveryStatus orderDeliveryStatus;
    private String orderDeliveryStatusValue;

    @Setter
    private List<OrderProductDto> orderProducts = new ArrayList<>();

    private DeliveryDto delivery;

    private Long deliveryId;
    private String recipientName;
    private String recipientPhoneNumber;
    private String requirement;
    private Address address;

    private Long orderProductCount;

    private Long exchangeReturnId;

    @Builder
    public OrderDto(Order order) {
        this.orderId = order.getId();
        this.userNo = order.getUser().getId();
        this.username = order.getUser().getUsername();
        this.phoneNumber = order.getPhoneNumber();
        this.paymentMethodType = order.getPaymentMethodType();
        this.depositorName = order.getDepositorName();
        this.depositDueDate = order.getDepositDueDate();
    }

    @QueryProjection
    public OrderDto(Long orderId, OrderType orderType,
                    LocalDateTime createdDate, LocalDateTime orderCancelDate,
                    Long userNo, String username, String name,
                    String phoneNumber, PaymentMethodType paymentMethodType, Integer paymentPrice,
                    PaymentStatus paymentStatus,
                    String depositAccount,
                    String depositorName, LocalDate depositDueDate,
                    OrderDeliveryStatus orderDeliveryStatus,
                    Long deliveryId,
                    String recipientName, String recipientPhoneNumber,
                    String requirement, Address address,
                    Integer usedPoint, Long exchangeReturnId) {
        this.orderId = orderId;
        this.orderType = orderType;
        this.orderTypeValue = orderType.getValue();
        this.createdDate = createdDate;
        this.orderCancelDate = orderCancelDate;
        this.userNo = userNo;
        this.username = username;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.paymentMethodType = paymentMethodType;
        this.paymentMethodTypeValue = paymentMethodType.getValue();
        this.paymentPrice = paymentPrice;
        this.paymentStatus = paymentStatus;
        this.paymentStatusValue = paymentStatus.getValue();
        this.depositAccount = depositAccount;
        this.depositorName = depositorName;
        this.depositDueDate = depositDueDate;
        this.orderDeliveryStatus = orderDeliveryStatus;
        this.orderDeliveryStatusValue = orderDeliveryStatus.getValue();
        this.deliveryId = deliveryId;
        this.recipientName = recipientName;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.requirement = requirement;
        this.address = address;
        this.usedPoint = ObjectUtils.isEmpty(usedPoint) ? 0 : usedPoint;
        this.exchangeReturnId = exchangeReturnId;
    }

    @QueryProjection
    public OrderDto(Long orderId, OrderType orderType,
                    LocalDateTime createdDate, LocalDateTime orderCancelDate,
                    Long userNo, String username, String name,
                    String phoneNumber, PaymentMethodType paymentMethodType, Integer paymentPrice,
                    String depositAccount,
                    String depositorName, LocalDate depositDueDate,
                    PaymentStatus paymentStatus,
                    OrderDeliveryStatus orderDeliveryStatus,
                    String recipientName, String recipientPhoneNumber,
                    String requirement, Address address,
                    Long orderProductCount, Long exchangeReturnId) {
        this.orderId = orderId;
        this.orderType = orderType;
        this.orderTypeValue = orderType.getValue();
        this.createdDate = createdDate;
        this.orderCancelDate = orderCancelDate;
        this.userNo = userNo;
        this.username = username;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.paymentMethodType = paymentMethodType;
        this.paymentMethodTypeValue = paymentMethodType.getValue();
        this.paymentPrice = paymentPrice;
        this.depositAccount = depositAccount;
        this.depositorName = depositorName;
        this.depositDueDate = depositDueDate;
        this.paymentStatusValue = paymentStatus.getValue();
        this.orderDeliveryStatus = orderDeliveryStatus;
        this.orderDeliveryStatusValue = orderDeliveryStatus.getValue();
        this.recipientName = recipientName;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.requirement = requirement;
        this.address = address;
        this.orderProductCount = orderProductCount;
        this.exchangeReturnId = exchangeReturnId;
    }
}
