package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.OrderProduct;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class OrderProductOptionDto {

    private Long orderProductOptionId;
    private Long orderProductId;
    private Long optionId;
    private Integer optionGroupNumber;
    private String optionName;
    private String optionValue;
    private OrderProduct orderProduct;

    @QueryProjection
    public OrderProductOptionDto(Long orderProductOptionId, Long orderProductId, Integer optionGroupNumber, String optionName, String optionValue) {
        this.orderProductOptionId = orderProductOptionId;
        this.orderProductId = orderProductId;
        this.optionGroupNumber = optionGroupNumber;
        this.optionName = optionName;
        this.optionValue = optionValue;
    }

    @QueryProjection
    public OrderProductOptionDto(Long orderProductOptionId, Long orderProductId, Long optionId, Integer optionGroupNumber, String optionName, String optionValue) {
        this.orderProductOptionId = orderProductOptionId;
        this.orderProductId = orderProductId;
        this.optionId = optionId;
        this.optionGroupNumber = optionGroupNumber;
        this.optionName = optionName;
        this.optionValue = optionValue;
    }

    @Builder
    public OrderProductOptionDto(Long optionId, Integer optionGroupNumber, String optionName, String optionValue, OrderProduct orderProduct) {
        this.optionId = optionId;
        this.optionGroupNumber = optionGroupNumber;
        this.optionName = optionName;
        this.optionValue = optionValue;
        this.orderProduct = orderProduct;
    }
}
