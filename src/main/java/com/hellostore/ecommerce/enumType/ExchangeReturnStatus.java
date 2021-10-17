package com.hellostore.ecommerce.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExchangeReturnStatus {
    REQUESTED("신청 완료"),
    PROCESSING("처리 중"),
    FINISHED("처리 완료");

    private final String value;
}
