package com.hellostore.ecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class OrderProductOptionDto {

    private Integer optionGroupNumber;
    private String optionName;
    private String optionValue;
}
