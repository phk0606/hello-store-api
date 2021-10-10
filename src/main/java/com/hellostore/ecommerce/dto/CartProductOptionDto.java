package com.hellostore.ecommerce.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class CartProductOptionDto {

    private Long cartProductOptionId;
    private Long cartProductId;
    private Long optionId;
    private Integer optionGroupNumber;
    private String optionName;
    private String optionValue;

    @QueryProjection
    public CartProductOptionDto(Long cartProductOptionId, Long cartProductId, Long optionId, Integer optionGroupNumber, String optionName, String optionValue) {
        this.cartProductOptionId = cartProductOptionId;
        this.cartProductId = cartProductId;
        this.optionId = optionId;
        this.optionGroupNumber = optionGroupNumber;
        this.optionName = optionName;
        this.optionValue = optionValue;
    }
}
