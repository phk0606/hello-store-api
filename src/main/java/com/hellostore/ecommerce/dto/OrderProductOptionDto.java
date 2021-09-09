package com.hellostore.ecommerce.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class OrderProductOptionDto {

    private Long orderProductOptionId;
    private Long orderProductId;
    private Integer optionGroupNumber;
    private String optionName;
    private String optionValue;

    @QueryProjection
    public OrderProductOptionDto(Long orderProductOptionId, Long orderProductId, Integer optionGroupNumber, String optionName, String optionValue) {
        this.orderProductOptionId = orderProductOptionId;
        this.orderProductId = orderProductId;
        this.optionGroupNumber = optionGroupNumber;
        this.optionName = optionName;
        this.optionValue = optionValue;
    }
}
