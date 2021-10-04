package com.hellostore.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ManToManQuestionSearchCondition {

    private Boolean noAnswer;
    private String searchText;
    private String username;
}
