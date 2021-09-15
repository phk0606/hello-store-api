package com.hellostore.ecommerce.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DeliveryStatus {
    READY_SHIP("배송 준비 중"),
    SHIPPING("배송 중"),
    COMPLETE_SHIP("배송 완료");

    private final String value;
}
