package com.hellostore.ecommerce.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentMethodType {
    WITHOUT_BANKBOOK("무통장 입급"),
    CREDIT_CARD("신용 카드"),
    ACCOUNT_TRANSFER("계좌 이체");

    private final String value;
}
