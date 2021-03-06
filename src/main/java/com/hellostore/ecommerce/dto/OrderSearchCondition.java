package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.enumType.OrderDeliveryStatus;
import com.hellostore.ecommerce.enumType.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderSearchCondition {

    private String username;
    private String orderDateA;
    private String orderDateB;

    private OrderDeliveryStatus orderDeliveryStatus;
    private PaymentStatus paymentStatus;
    private Long orderId;
    private String productName;
    private String ordererId;
    private String ordererName;
}
