package com.hellostore.ecommerce.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProductShowType {
    SHOW("진열"),
    HIDE("숨김"),
    SOLDOUT("품절");

    private final String value;
}
