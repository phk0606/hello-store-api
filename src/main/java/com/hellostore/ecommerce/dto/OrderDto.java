package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.Delivery;
import com.hellostore.ecommerce.enumType.PaymentMethodType;
import com.hellostore.ecommerce.enumType.PaymentStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private String phoneNumber;

    private PaymentMethodType paymentMethodType;
    private PaymentStatus paymentStatus;

    private String depositAccount;
    private String depositorName;
    private LocalDate depositDueDate;

    private List<OrderProductDto> orderProducts = new ArrayList<>();
    private DeliveryDto delivery;

    private Long orderProductId;
    private int orderQuantity;
    private Integer orderShippingFee;
    private Integer point;
    private int salePrice;
    private int totalPrice;

    private Long deliveryId;
    private String zoneCode;
    private String address;
    private String detailAddress;
    private String recipientName;
    private String recipientPhoneNumber;
    private String requirement;

    @QueryProjection
    public OrderDto(Long orderId, String username, String phoneNumber, PaymentMethodType paymentMethodType,
                    Long productId, int orderQuantity, Integer orderShippingFee, Integer point,
                    int salePrice, int totalPrice,
                    Long deliveryId, String zoneCode, String address, String detailAddress,
                    String recipientName, String recipientPhoneNumber, String requirement) {
        this.orderId = orderId;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.paymentMethodType = paymentMethodType;
        this.productId = productId;
        this.orderQuantity = orderQuantity;
        this.orderShippingFee = orderShippingFee;
        this.point = point;
        this.salePrice = salePrice;
        this.totalPrice = totalPrice;
        this.deliveryId = deliveryId;
        this.zoneCode = zoneCode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.recipientName = recipientName;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.requirement = requirement;
    }
}
