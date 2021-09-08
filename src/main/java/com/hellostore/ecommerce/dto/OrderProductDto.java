package com.hellostore.ecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class OrderProductDto {

    private Long productId;
    private int salePrice;
    private int orderQuantity;
    private int point;
    private int orderShippingFee;
    private int totalPrice;
}
