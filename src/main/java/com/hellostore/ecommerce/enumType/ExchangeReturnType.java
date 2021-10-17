package com.hellostore.ecommerce.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExchangeReturnType {

    EXCHANGE("교환"),
    REFUND("환불");

    private final String value;
}
