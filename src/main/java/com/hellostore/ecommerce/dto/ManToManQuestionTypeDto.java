package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.enumType.ManToManQuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ManToManQuestionTypeDto {

    private ManToManQuestionType manToManQuestionType;
    private String manToManQuestionTypeValue;
}
