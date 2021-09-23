package com.hellostore.ecommerce.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ProductSelectDto {

    private Long productId;
    private String productName;

    @QueryProjection
    public ProductSelectDto(Long productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }
}
