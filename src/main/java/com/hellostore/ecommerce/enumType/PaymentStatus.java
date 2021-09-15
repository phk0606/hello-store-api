package com.hellostore.ecommerce.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentStatus {
    BEFORE("결제 전"),
    FINISHED("결제 완료"),
    CANCEL("결제 취소");

    private final String value;
}
