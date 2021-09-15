package com.hellostore.ecommerce.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderStatus {
    BEFORE_CONFIRM("주문 확인 전"),
    CONFIRM_ORDER("주문 확인"),
    ORDER_CANCEL("주문 취소");

    private final String value;
}
