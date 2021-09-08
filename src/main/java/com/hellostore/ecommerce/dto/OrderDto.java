package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.enumType.PaymentMethodType;
import com.hellostore.ecommerce.enumType.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class OrderDto {

    private Long userNo;
    private String username;
    private Long productId;
    private int orderQuantity;

    private String zoneCode;
    private String address;
    private String detailAddress;

    private String phoneNumber;
    private String requirement;

    private PaymentMethodType paymentMethodType;
    private PaymentStatus paymentStatus;

    List<OrderProductDto> orderProductDtos = new ArrayList<>();
}
