package com.hellostore.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class ProductQnADto {

    private Long productId;
    private Long productQuestionId;
    private String questionUsername;
    private String questionContent;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime questionCreatedDate;

    private Long productAnswerId;
    private String answerUsername;
    private String answerContent;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime answerCreatedDate;

    private String productName;
    private String categoryName;

    @QueryProjection
    public ProductQnADto(Long productId, Long productQuestionId,
                         String questionUsername, String questionContent,
                         LocalDateTime questionCreatedDate, Long productAnswerId,
                         String answerUsername, String answerContent,
                         LocalDateTime answerCreatedDate,
                         String productName,
                         String categoryName) {
        this.productId = productId;
        this.productQuestionId = productQuestionId;
        this.questionUsername = questionUsername;
        this.questionContent = questionContent;
        this.questionCreatedDate = questionCreatedDate;
        this.productAnswerId = productAnswerId;
        this.answerUsername = answerUsername;
        this.answerContent = answerContent;
        this.answerCreatedDate = answerCreatedDate;
        this.productName = productName;
        this.categoryName = categoryName;
    }
}
