package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.enumType.ExchangeRefundType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ExchangeRefundProductDto {

    private Long orderProductId;

    private ExchangeRefundType exchangeRefundType;
    private String exchangeRefundTypeValue;

    private Long exchangeRefundId;
    private Long exchangeRefundProductId;

    private String productName;

    @QueryProjection
    public ExchangeRefundProductDto(ExchangeRefundType exchangeRefundType, Long exchangeRefundId, Long exchangeRefundProductId, String productName) {
        this.exchangeRefundId = exchangeRefundId;
        this.exchangeRefundProductId = exchangeRefundProductId;
        this.exchangeRefundType = exchangeRefundType;
        this.exchangeRefundTypeValue = exchangeRefundType.getValue();
        this.productName = productName;
    }
}
