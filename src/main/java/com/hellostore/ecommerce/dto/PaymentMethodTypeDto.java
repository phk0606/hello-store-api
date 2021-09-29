package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.enumType.PaymentMethodType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class PaymentMethodTypeDto {

    private PaymentMethodType paymentMethodType;
    private String paymentMethodTypeValue;

    @QueryProjection
    public PaymentMethodTypeDto(PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
        this.paymentMethodTypeValue = paymentMethodType.getValue();
    }
}
