package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@NoArgsConstructor
@ToString
@Slf4j
public class ProductCategorySelectDto {

    private String text;
    private Integer value;

    public ProductCategorySelectDto(ProductCategory productCategory) {
        this.text = productCategory.getName();
        this.value = productCategory.getId();
    }
}
