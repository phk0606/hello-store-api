package com.hellostore.ecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class QnASearchCondition {

    private Long productId;
    private Boolean noAnswer;
    private String searchText;
}
