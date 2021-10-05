package com.hellostore.ecommerce.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExchangeRefundReasonType {

    COLOR_SIZE_CHANGE("색상/사이즈 변경"),
    MIND_CHANGE("단순 변심"),
    PRODUCT_BROKEN("제품 파손"),
    MISTAKE_SHIPPING("오배송"),
    ETC("기타");

    private final String value;
}
