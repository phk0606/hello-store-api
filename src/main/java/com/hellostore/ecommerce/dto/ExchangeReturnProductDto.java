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

    private Long productId;
    private String productName;

    private int salePrice;
    private int quantity;
    private Integer point;

    private String filePath;
    private String fileName;

    @Setter
    private List<OrderProductOptionDto> productOptions = new ArrayList<>();

    @Setter
    private List<ProductOptionDto> firstOptions = new ArrayList<>();
    private ProductOptionDto newFirstOption;
    @Setter
    private List<ProductOptionDto> secondOptions = new ArrayList<>();
    private ProductOptionDto newSecondOption;

    private Long newOrderId;

    @QueryProjection
    public ExchangeReturnProductDto(Long orderProductId, ExchangeReturnType exchangeReturnType, Long exchangeReturnId, Long exchangeReturnProductId, Long productId, String productName, int salePrice, int quantity, Integer point, String filePath, String fileName, Long newOrderId) {
        this.orderProductId = orderProductId;
        this.exchangeReturnId = exchangeReturnId;
        this.exchangeReturnProductId = exchangeReturnProductId;
        this.exchangeReturnType = exchangeReturnType;
        this.exchangeReturnTypeValue = exchangeReturnType.getValue();
        this.productId = productId;
        this.productName = productName;
        this.salePrice = salePrice;
        this.quantity = quantity;
        this.point = point;
        this.filePath = filePath;
        this.fileName = fileName;
        this.newOrderId = newOrderId;
    }
}
