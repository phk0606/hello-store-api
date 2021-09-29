package com.hellostore.ecommerce.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PointUseDetailType {

    SIGNUP("회원가입"),
    PURCHASE("제품 구매"),
    PURCHASE_CANCEL("제품 구매 취소"),
    A_YEAR_UNUSED("1년간 미사용");

    private final String value;
}
