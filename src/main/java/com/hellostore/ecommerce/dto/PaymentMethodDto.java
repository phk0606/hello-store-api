package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.enumType.PaymentMethodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class PaymentMethodDto {

    private List<PaymentMethodType> paymentMethodTypes;
}
