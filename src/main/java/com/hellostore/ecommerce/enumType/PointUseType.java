package com.hellostore.ecommerce.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PointUseType {

    SAVE("적립"), // 제품 구매, 회원 가입
    USE("사용"), // 제품 구매
    SAVE_CANCEL("적립 취소"), // 제품 구매 취소
    EXTINCTION("소멸"); // 1년 이상 미사용 시

    private final String value;
}
