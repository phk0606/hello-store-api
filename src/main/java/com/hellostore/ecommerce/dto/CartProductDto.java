package com.hellostore.ecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class CartProductDto {

    private Long productId;
    private int quantity;

    private String firstOptionName;
    private String firstOptionValue;

    private String secondOptionName;
    private String secondOptionValue;

}
