package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.enumType.OrderType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ExchangeReturnOrderDto {

    private Long orderId;
    private Long productId;
    private Long orderProductId;
    private Long newFirstOptionId;
    private Long newSecondOptionId;

    private OrderType orderType;
    private String username;

    private Long exchangeReturnProductId;
}
