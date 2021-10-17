package com.hellostore.ecommerce.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderDeliveryStatus {
    ORDER_CONFIRM_BEFORE("주문 확인 전"),
    ORDER_CONFIRM_COMPLETE("주문 확인 완료"),
    ORDER_CANCEL_PROCESS("주문 취소 처리 중"),
    ORDER_CANCEL_COMPLETE("주문 취소 완료"),
    SHIPPING_READY("배송 준비 중"),
    SHIPPING("배송 중"),
    SHIPPING_COMPLETE("배송 완료");

    private final String value;
}
