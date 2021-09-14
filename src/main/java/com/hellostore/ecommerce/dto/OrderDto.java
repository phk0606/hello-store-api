package com.hellostore.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hellostore.ecommerce.entity.Address;
import com.hellostore.ecommerce.entity.Order;
import com.hellostore.ecommerce.enumType.DeliveryStatus;
import com.hellostore.ecommerce.enumType.OrderStatus;
import com.hellostore.ecommerce.enumType.PaymentMethodType;
import com.hellostore.ecommerce.enumType.PaymentStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class OrderDto {

    private Long orderId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    private Long userNo;
    private String username;
    private String name;
    private String phoneNumber;

    private PaymentMethodType paymentMethodType;
    private PaymentStatus paymentStatus;
    private Integer paymentPrice;

    private String depositAccount;
    private String depositorName;
    private LocalDate depositDueDate;
    private OrderStatus orderStatus;

    @Setter
    private List<OrderProductDto> orderProducts = new ArrayList<>();

    private DeliveryDto delivery;
    private DeliveryStatus deliveryStatus;

    private String recipientName;
    private String recipientPhoneNumber;
    private String requirement;
    private Address address;

    private String filePath;
    private String fileName;
    @Setter
    private byte[] image;

    private Long orderProductCount;
    String productName;

    @Builder
    public OrderDto(Order order) {
        this.orderId = order.getId();
        this.userNo = order.getUser().getId();
        this.username = order.getUser().getUsername();
        this.phoneNumber = order.getPhoneNumber();
        this.paymentMethodType = order.getPaymentMethodType();
        this.depositAccount = order.getDepositAccount();
        this.depositorName = order.getDepositorName();
        this.depositDueDate = order.getDepositDueDate();
    }

    @QueryProjection
    public OrderDto(Long orderId, Long userNo, String username, String name,
                    String phoneNumber, PaymentMethodType paymentMethodType, Integer paymentPrice,
                    String depositAccount, String depositorName, LocalDate depositDueDate,
                    String recipientName, String recipientPhoneNumber,
                    String requirement, Address address) {
        this.orderId = orderId;
        this.userNo = userNo;
        this.username = username;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.paymentMethodType = paymentMethodType;
        this.paymentPrice = paymentPrice;
        this.depositAccount = depositAccount;
        this.depositorName = depositorName;
        this.depositDueDate = depositDueDate;
        this.recipientName = recipientName;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.requirement = requirement;
        this.address = address;
    }

    @QueryProjection
    public OrderDto(Long orderId, LocalDateTime createdDate, Long userNo, String username, String name,
                    String phoneNumber, PaymentMethodType paymentMethodType, Integer paymentPrice,
                    String depositAccount, String depositorName, LocalDate depositDueDate,
                    PaymentStatus paymentStatus,
                    OrderStatus orderStatus,
                    String recipientName, String recipientPhoneNumber,
                    String requirement, Address address,
                    DeliveryStatus deliveryStatus,
                    String filePath, String fileName,
                    Long orderProductCount, String productName) {
        this.orderId = orderId;
        this.createdDate = createdDate;
        this.userNo = userNo;
        this.username = username;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.paymentMethodType = paymentMethodType;
        this.paymentPrice = paymentPrice;
        this.depositAccount = depositAccount;
        this.depositorName = depositorName;
        this.depositDueDate = depositDueDate;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
        this.recipientName = recipientName;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.requirement = requirement;
        this.address = address;
        this.deliveryStatus = deliveryStatus;
        this.filePath = filePath;
        this.fileName = fileName;
        this.orderProductCount = orderProductCount;
        this.productName = productName;
    }
}
