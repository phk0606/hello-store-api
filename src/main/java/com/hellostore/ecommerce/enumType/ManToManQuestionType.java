package com.hellostore.ecommerce.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ManToManQuestionType {

    SHIPPING("배송 지연", 1),
    RETURN("반품", 2),
    AFTER_SERVICE("A/S", 3),
    REFUND("환불", 4),
    ORDER("주문 결제" , 5),
    USER_INFO("회원 정보" , 6),
    CANCEL("취소" , 7),
    EXCHANGE("교환", 8),
    PRODUCT_INFO("상품 정보", 9),
    SOLD_OUT("품절/재입고", 10),
    ETC("기타", 11);

    private final String value;
    private final int sequence;
}
