package com.hellostore.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hellostore.ecommerce.enumType.ManToManQuestionType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class ManToManQnADto {

    private Long manToManQuestionId;
    private String manToManQuestionTitle;
    private String manToManQuestionContent;

    private ManToManQuestionType manToManQuestionType;

    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    private Long manToManAnswerId;
    private String manToManAnswerContent;

    private String answerCreatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime answerCreatedDate;

    @QueryProjection
    public ManToManQnADto(Long manToManQuestionId, String manToManQuestionTitle, String manToManQuestionContent, ManToManQuestionType manToManQuestionType, String createdBy, LocalDateTime createdDate, Long manToManAnswerId, String manToManAnswerContent, String answerCreatedBy, LocalDateTime answerCreatedDate) {
        this.manToManQuestionId = manToManQuestionId;
        this.manToManQuestionTitle = manToManQuestionTitle;
        this.manToManQuestionContent = manToManQuestionContent;
        this.manToManQuestionType = manToManQuestionType;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.manToManAnswerId = manToManAnswerId;
        this.manToManAnswerContent = manToManAnswerContent;
        this.answerCreatedBy = answerCreatedBy;
        this.answerCreatedDate = answerCreatedDate;
    }
}
