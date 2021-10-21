package com.hellostore.ecommerce.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderType {

    NEW("신규"),
    EXCHANGE("교환"),
    RETURN("반품");

    private final String value;
}
