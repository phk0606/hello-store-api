package com.hellostore.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductCommentSearchCondition {

    private Long productId;
    private String productName;
    private String username;
}
