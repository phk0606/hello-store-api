package com.hellostore.ecommerce.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class StockQuantityDto {

    private Long stockQuantityId;

    private Long productId;
    private String productName;
    private Long firstOptionId;
    private String firstOptionName;
    private String firstOptionValue;
    private Long secondOptionId;
    private String secondOptionName;
    private String secondOptionValue;

    private int stockQuantity;

    @QueryProjection
    public StockQuantityDto(Long stockQuantityId, Long productId, String productName, Long firstOptionId, String firstOptionName, String firstOptionValue, Long secondOptionId, String secondOptionName, String secondOptionValue, int stockQuantity) {
        this.stockQuantityId = stockQuantityId;
        this.productId = productId;
        this.productName = productName;
        this.firstOptionId = firstOptionId;
        this.firstOptionName = firstOptionName;
        this.firstOptionValue = firstOptionValue;
        this.secondOptionId = secondOptionId;
        this.secondOptionName = secondOptionName;
        this.secondOptionValue = secondOptionValue;
        this.stockQuantity = stockQuantity;
    }

    @Builder
    public StockQuantityDto(Long productId, Long firstOptionId, Long secondOptionId, int stockQuantity) {
        this.productId = productId;
        this.firstOptionId = firstOptionId;
        this.secondOptionId = secondOptionId;
        this.stockQuantity = stockQuantity;
    }
}
