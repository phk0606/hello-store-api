package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.enumType.ExchangeReturnType;
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
public class ExchangeReturnProductDto {

    private Long orderProductId;

    private ExchangeReturnType exchangeReturnType;
    private String exchangeReturnTypeValue;

    private Long exchangeReturnId;
    private Long exchangeReturnProductId;

    private String productName;

    private int salePrice;
    private int quantity;

    private String filePath;
    private String fileName;

    @Setter
    private List<OrderProductOptionDto> productOptions = new ArrayList<>();

    @QueryProjection
    public ExchangeReturnProductDto(Long orderProductId, ExchangeReturnType exchangeReturnType, Long exchangeReturnId, Long exchangeReturnProductId, String productName, int salePrice, int quantity, String filePath, String fileName) {
        this.orderProductId = orderProductId;
        this.exchangeReturnId = exchangeReturnId;
        this.exchangeReturnProductId = exchangeReturnProductId;
        this.exchangeReturnType = exchangeReturnType;
        this.exchangeReturnTypeValue = exchangeReturnType.getValue();
        this.productName = productName;
        this.salePrice = salePrice;
        this.quantity = quantity;
        this.filePath = filePath;
        this.fileName = fileName;
    }
}
