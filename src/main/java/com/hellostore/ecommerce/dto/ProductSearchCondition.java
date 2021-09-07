package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.enumType.ProductShowType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductSearchCondition {

    private String productName;
    private Long firstCategoryId;
    private Long secondCategoryId;
    private Integer salePriceMin;
    private Integer salePriceMax;
    private List<ProductShowType> productShowTypes;
    private String productRegistryDateA;
    private String productRegistryDateB;
}
