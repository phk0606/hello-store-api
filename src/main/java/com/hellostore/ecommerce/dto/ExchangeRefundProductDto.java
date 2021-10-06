package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.enumType.ExchangeRefundType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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

    private int salePrice;
    private int quantity;

    private String filePath;
    private String fileName;

    @Setter
    private List<OrderProductOptionDto> productOptions = new ArrayList<>();

    @QueryProjection
    public ExchangeRefundProductDto(Long orderProductId, ExchangeRefundType exchangeRefundType, Long exchangeRefundId, Long exchangeRefundProductId, String productName, int salePrice, int quantity, String filePath, String fileName) {
        this.orderProductId = orderProductId;
        this.exchangeRefundId = exchangeRefundId;
        this.exchangeRefundProductId = exchangeRefundProductId;
        this.exchangeRefundType = exchangeRefundType;
        this.exchangeRefundTypeValue = exchangeRefundType.getValue();
        this.productName = productName;
        this.salePrice = salePrice;
        this.quantity = quantity;
        this.filePath = filePath;
        this.fileName = fileName;
    }
}
