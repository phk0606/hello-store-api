package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.enumType.FaqType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FaqSearchCondition {

    private String searchText;
    private FaqType faqType;
}
