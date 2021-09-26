package com.hellostore.ecommerce.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentMethodType {
    WITHOUT_BANKBOOK("무통장 입급"),
    CREDIT_CARD("신용 카드"),
    ACCOUNT_TRANSFER("계좌 이체"),
    VIRTUAL_ACCOUNT("가상 계좌"),
    MOBILE_PAYMENT("휴대폰 결제"),
    ESCROW_CREDIT_CARD("에스크로우 신용카드"),
    ESCROW_ACCOUNT_TRANSFER("에스크로우 계좌이체");

    private final String value;
}
