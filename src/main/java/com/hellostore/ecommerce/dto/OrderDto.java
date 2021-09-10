package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.Address;
import com.hellostore.ecommerce.entity.Order;
import com.hellostore.ecommerce.enumType.PaymentMethodType;
import com.hellostore.ecommerce.enumType.PaymentStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class OrderDto {

    private Long orderId;
    private Long productId;
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

    @Setter
    private List<OrderProductDto> orderProducts = new ArrayList<>();

    private DeliveryDto delivery;

    private String recipientName;
    private String recipientPhoneNumber;
    private String requirement;
    private Address address;

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

    //    private Long orderProductId;
//    private int orderQuantity;
//    private Integer orderShippingFee;
//    private Integer point;
//    private int salePrice;
//    private int totalPrice;
//
//    private Long deliveryId;
//    private String zoneCode;
//    private String address;
//    private String detailAddress;
//    private String recipientName;
//    private String recipientPhoneNumber;
//    private String requirement;
//
//    @QueryProjection
//    public OrderDto(Long orderId, String username, String phoneNumber, PaymentMethodType paymentMethodType,
//                    Long productId, int orderQuantity, Integer orderShippingFee, Integer point,
//                    int salePrice, int totalPrice,
//                    Long deliveryId, String zoneCode, String address, String detailAddress,
//                    String recipientName, String recipientPhoneNumber, String requirement) {
//        this.orderId = orderId;
//        this.username = username;
//        this.phoneNumber = phoneNumber;
//        this.paymentMethodType = paymentMethodType;
//        this.productId = productId;
//        this.orderQuantity = orderQuantity;
//        this.orderShippingFee = orderShippingFee;
//        this.point = point;
//        this.salePrice = salePrice;
//        this.totalPrice = totalPrice;
//        this.deliveryId = deliveryId;
//        this.zoneCode = zoneCode;
//        this.address = address;
//        this.detailAddress = detailAddress;
//        this.recipientName = recipientName;
//        this.recipientPhoneNumber = recipientPhoneNumber;
//        this.requirement = requirement;
//    }
}
