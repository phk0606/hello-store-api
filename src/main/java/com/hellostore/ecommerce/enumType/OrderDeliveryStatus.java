package com.hellostore.ecommerce.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderDeliveryStatus {
    BEFORE_CONFIRM("주문 확인 전"),
    CONFIRM_ORDER("주문 확인"),
    ORDER_CANCEL("주문 취소"),
    READY_SHIP("배송 준비 중"),
    SHIPPING("배송 중"),
    COMPLETE_SHIP("배송 완료");

    private final String value;
}
