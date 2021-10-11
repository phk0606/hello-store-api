package com.hellostore.ecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StockQuantitySearchCondition {

    private Integer stockQuantityMin;
    private Integer stockQuantityMax;
    private String searchText;
    private Long productId;
    private Long firstOptionId;
    private Long secondOptionId;
}
