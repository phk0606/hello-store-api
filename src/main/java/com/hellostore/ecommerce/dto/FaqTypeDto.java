package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.enumType.FaqType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FaqTypeDto {

    private FaqType faqType;
    private String faqTypeValue;
}
