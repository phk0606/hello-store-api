package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.enumType.ExchangeRefundType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ExchangeRefundProductDto {

    private Long orderProductId;
    private ExchangeRefundType exchangeRefundType;
}
