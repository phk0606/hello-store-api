package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.ProductOption;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ProductOptionDto {

    private Long id;

    private Integer optionGroupNumber;
    private String optionName;
    private String optionValue;

    public ProductOptionDto(ProductOption productOption) {
        this.id = productOption.getId();
        this.optionGroupNumber = productOption.getOptionGroupNumber();
        this.optionName = productOption.getOptionName();
        this.optionValue = productOption.getOptionValue();
    }
}
