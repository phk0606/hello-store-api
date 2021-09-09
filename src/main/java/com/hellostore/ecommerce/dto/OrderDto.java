package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.Delivery;
import com.hellostore.ecommerce.enumType.PaymentMethodType;
import com.hellostore.ecommerce.enumType.PaymentStatus;
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
}
