package com.hellostore.ecommerce.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class CartProductDto {

    private Long cartProductId;
    private Long productId;
    private int quantity;

    private String firstOptionName;
    private String firstOptionValue;

    private String secondOptionName;
    private String secondOptionValue;

    @QueryProjection
    public CartProductDto(Long cartProductId, Long productId, int quantity, String firstOptionName, String firstOptionValue, String secondOptionName, String secondOptionValue) {
        this.cartProductId = cartProductId;
        this.productId = productId;
        this.quantity = quantity;
        this.firstOptionName = firstOptionName;
        this.firstOptionValue = firstOptionValue;
        this.secondOptionName = secondOptionName;
        this.secondOptionValue = secondOptionValue;
    }
}
