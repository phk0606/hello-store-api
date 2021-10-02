package com.hellostore.ecommerce.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FaqType {

    ORDER("주문" , 1),
    SHIPPING("배송", 2),
    PRODUCT("상품", 3),
    EXCHANGE("교환/반품", 4),
    CANCEL("취소", 5),
    USER_INFO("회원정보", 6),
    SOLD_OUT("품절/재입고", 7),
    ETC("기타", 8);

    private final String value;
    private final int sequence;
}
