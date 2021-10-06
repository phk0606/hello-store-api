package com.hellostore.ecommerce.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExchangeRefundStatus {
    BEFORE("처리 전"),
    FINISHED("처리 완료");

    private final String value;
}
