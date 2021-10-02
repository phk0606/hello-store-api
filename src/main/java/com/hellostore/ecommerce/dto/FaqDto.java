package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.enumType.FaqType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class FaqDto {

    private Long faqId;
    private String question;
    private String answer;
    private FaqType faqType;
    private String faqTypeValue;

    @QueryProjection
    public FaqDto(Long faqId, String question, String answer, FaqType faqType) {
        this.faqId = faqId;
        this.question = question;
        this.answer = answer;
        this.faqType = faqType;
    }
}
