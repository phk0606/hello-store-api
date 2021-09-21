package com.hellostore.ecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ProductCommentDto {

    private Long productCommentId;
    private Long orderId;
    private String username;
    private Long productId;
    private String content;
    private int grade;

}
