package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.ProductOption;
import com.querydsl.core.annotations.QueryProjection;
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

    public static ProductOption toEntity(ProductOptionDto productOptionDto) {
        return ProductOption.builder()
                .id(productOptionDto.getId())
                .optionGroupNumber(productOptionDto.getOptionGroupNumber())
                .optionName(productOptionDto.getOptionName())
                .optionValue(productOptionDto.getOptionValue())
                .build();
    }

    @QueryProjection
    public ProductOptionDto(Long id, Integer optionGroupNumber, String optionName, String optionValue) {
        this.id = id;
        this.optionGroupNumber = optionGroupNumber;
        this.optionName = optionName;
        this.optionValue = optionValue;
    }
}
